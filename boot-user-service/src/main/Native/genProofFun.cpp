
#include "genProofFun.h"
#define DBG_NEGEXP

CryptoPP::Integer rnd_commitment(const Parameters &pp, CryptoPP::RandomNumberGenerator &rng) {
  return CryptoPP::Integer(rng, pp.rnd_bitsize_commitment);
}

CryptoPP::Integer neg_a_exp_b_mod_c(const CryptoPP::Integer& a, const CryptoPP::Integer& b, const CryptoPP::ModularArithmetic& c) {
  CryptoPP::Integer interm, result;

  if(b < 0) {
    interm = c.Exponentiate(a, -b);
    result = c.MultiplicativeInverse(interm);
  } else {
    result = c.Exponentiate(a, b);
#ifdef DBG_NCOMM
    std::cout<<"result :"<<result << std::endl;
#endif
  }
  return result;
}

CryptoPP::Integer CreateCommitment(const Parameters &pp, const CryptoPP::Integer x, const CryptoPP::Integer y, const CryptoPP::Integer z, const CryptoPP::Integer r) {
  CryptoPP::Integer s;

#ifdef DBG_NEGEXP
  CryptoPP::Integer var1 = pp.group.Multiply(
          	  	  	  	  	  neg_a_exp_b_mod_c(pp.gz, z, pp.group),
          	  	  	  	  	  	  	  neg_a_exp_b_mod_c(pp.g, r, pp.group));
#ifdef DBG_NCOMM
  std::cout<<"var1 :"<<var1 << std::endl;
#endif
  CryptoPP::Integer var2 = pp.group.Multiply(neg_a_exp_b_mod_c(pp.gy, y, pp.group),var1);
#ifdef DBG_NCOMM
  std::cout<<"var2 :"<<var2 << std::endl;
#endif
  s = pp.group.Multiply(
          neg_a_exp_b_mod_c(pp.gx, x, pp.group),var2);
#ifdef DBG_NCOMM
  std::cout<<"s :"<<s << std::endl;
#endif
#else
  s = pp.group.Multiply(
	  pp.group.Exponentiate(pp.gx, x),
	  pp.group.Multiply(
	     pp.group.Exponentiate(pp.gy, y),
             pp.group.Multiply(
		pp.group.Exponentiate(pp.gz, z),
                pp.group.Exponentiate(pp.gr, r))));
#endif

#ifdef DBG_LOCCOMM
  std::cout << "x " << x << " y " << y << " z " << z << " r " << r << std::endl;
  std::cout << "location comm " << s << std::endl;
#endif
  return s;
}

CryptoPP::Integer CreateACommitment(const Parameters &pp, const CryptoPP::Integer crnd, const CryptoPP::Integer a[]) {
  CryptoPP::Integer s = pp.group.Exponentiate(pp.g, crnd), t;

  for(int j=0; j<4; j++) {
    //    std::cout << s <<  " _ ";
    t = pp.group.Multiply(s, pp.group.Exponentiate(pp.h[j], a[j]));
    s = t;
#ifdef DBG_ACOMM
    std::cout << a[j] << " " << t <<  " | ";
#endif
  }
#ifdef DBG_ACOMM
  std::cout << std::endl << "a4 commitment " << s << std::endl;
#endif
  return s;
}

CryptoPP::Integer CreateNCommitment(const Parameters &pp, const CryptoPP::Integer f, const CryptoPP::Integer rho) {
  CryptoPP::Integer s, t;
#ifdef DBG_NEGEXP
  t = neg_a_exp_b_mod_c(pp.g, f, pp.group);
#else
  if(f < 0) {
    s = pp.group.Exponentiate(pp.g, -f);
    t = pp.group.MultiplicativeInverse(s);
  } else {
    t = pp.group.Exponentiate(pp.g, f);
  }
#endif
  s = pp.group.Multiply(t,
#ifdef DBG_NEGEXP
        neg_a_exp_b_mod_c(pp.gr, rho, pp.group));
#else
        pp.group.Exponentiate(pp.gr, rho));
#endif

#ifdef DBG_NCOMM
  std::cout << "f " << f << std::endl;
  std::cout << "rho " << rho << std::endl;
  std::cout << "exp(f) " << t << std::endl;
  std::cout << "comm   " << s << std::endl;
#endif
  return s;
}

/*void Prover::step_challenge(CryptoPP::RandomNumberGenerator &rng) {

#ifdef DBG_CHALL0
  c = 0;
  return;
#endif
#ifdef DBG_CHALL1
  c = 1;
  return;
#endif
  c = CryptoPP::Integer(rng, pp.rnd_bitsize_chall);

}*/

void Prover::step_start(CryptoPP::RandomNumberGenerator &rng) {
  privi.gamma = rnd_commitment(pp, rng);
  ic.sa = CreateACommitment(pp, privi.gamma, privi.a);

  privpf.eta = rnd_commitment(pp, rng);
  for(int j=0; j<4; j++)
#ifdef DBG_CHALL1
    privpf.alpha[j] = 0;
#else
    privpf.alpha[j] = rnd_commitment(pp, rng);
#endif
  ic.t_a = CreateACommitment(pp, privpf.eta, privpf.alpha);

#ifdef DBG_CHALL1
  privpf.beta_x = 0;
  privpf.beta_y = 0;
  privpf.beta_z = 0;
  privpf.beta_r = 0;
#else
  privpf.beta_x = rnd_commitment(pp, rng);
  privpf.beta_y = rnd_commitment(pp, rng);
  privpf.beta_z = rnd_commitment(pp, rng);
  privpf.beta_r = rnd_commitment(pp, rng);
#endif
  ic.t_n = CreateCommitment(pp, privpf.beta_x, privpf.beta_y, privpf.beta_z, privpf.beta_r);

  privpf.f_0 = -(privpf.beta_x * privpf.beta_x + privpf.beta_y * privpf.beta_y + privpf.beta_z * privpf.beta_z);
  privpf.f_1 = (privi.x - pubi.xl)/privpf.beta_x + (privi.y - pubi.yl)/privpf.beta_y + (privi.z - pubi.zl)/privpf.beta_z;

  for(int j=0; j<4; j++) {
    privpf.f_0 -= privpf.alpha[j] * privpf.alpha[j];
    privpf.f_1 +=  privi.a[j] * privpf.alpha[j];
  }
  privpf.f_1 *= -2;
  privpf.rho_0 = rnd_commitment(pp, rng);
  privpf.rho_1 = rnd_commitment(pp, rng);

  ic.b_0 = CreateNCommitment(pp, privpf.f_0, privpf.rho_0);
  ic.b_1 = CreateNCommitment(pp, privpf.f_1, privpf.rho_1);
}

void Prover::step_responses() {
  rsp.Xn = c*privi.x + privpf.beta_x;
  rsp.Yn = c*privi.y + privpf.beta_y;
  rsp.Zn = c*privi.z + privpf.beta_z;
  rsp.R = c*privi.r + privpf.beta_r;

  for(int j=0; j<4; j++)
    rsp.A[j] = c*privi.a[j] + privpf.alpha[j];

  rsp.R_a = c*privi.gamma + privpf.eta;
  rsp.R_d = c*privpf.rho_1 + privpf.rho_0;
}

void SetParameters(Parameters &pp, RandomNumberGenerator &rng) {
  pp.rnd_bitsize_modulus = 2048;
  pp.rnd_bitsize_commitment = 200;
  pp.rnd_bitsize_chall = 50;
  pp.rnd_offset_chall = 150;

  InvertibleRSAFunction pv;
  pv.Initialize(rng, pp.rnd_bitsize_modulus, 3);
  pp.n = pv.GetPrime1() * pv.GetPrime2();

// (19 * 10 + 1)*(103 = 17 * 6 + 1) = 191*103 = 19673;  order 17*19 = 323, co-order 30
  pp.n = 19673;
  pp.group.SetModulus(pp.n);

// 4323 = 4^30%19673
// 4323^323%19673 = 1
  pp.g = 4323;

  pp.gx = 18652;
  pp.gy = 12642;
  pp.gz = 19445;
  pp.gr = 17679;
  pp.h[0] = 16385;
  pp.h[1] = 9555;
  pp.h[2] = 12638;
  pp.h[3] = 2153;
}


void set_node_location(Prover &p, CryptoPP::RandomNumberGenerator &rng, const int xn, const int yn, const int zn) {

	p.privi.x = xn;
	p.privi.y = yn;
	p.privi.z = zn;
	p.privi.r = rnd_commitment(p.pp, rng);

	CryptoPP::Integer scomm;
	scomm = CreateCommitment(p.pp, p.privi.x, p.privi.y, p.privi.z, p.privi.r);
	p.pubi.su = scomm;
}

void set_airdrop_location(Prover &p, int xl, int yl, int zl, int RR) {
  p.pubi.xl = xl;
  p.pubi.yl = yl;
  p.pubi.zl = zl;
  p.pubi.radius = RR;
}

long distance_meters(double latoriginrad, double longoriginrad, double latdestrad, double longdestrad){
  double gradToRad=0.0175;
  latoriginrad=latoriginrad*gradToRad;
  longoriginrad=longoriginrad*gradToRad;
  latdestrad=latdestrad*gradToRad;
  longdestrad=longdestrad*gradToRad;
  //cout << "latoriginrad " << latoriginrad << endl;
  //cout << "longoriginrad " << longoriginrad << endl;
  double HalfPi = 1.5707963;
  double R = 3956000; /* the radius gives you the measurement unit*/

  double a = HalfPi - latoriginrad;
  double b = HalfPi - latdestrad;
  double u = a * a + b * b;
  double v = - 2 * a * b * cos(longdestrad - longoriginrad);
  double c = sqrt(fabs(u + v));
  return (long) (R * c);
}

long get_airdrop_radius(Prover &p) {
  long dist_ln, diff_dist, approx;
  CryptoPP::Integer d2 =
    (p.privi.x - p.pubi.xl) * (p.privi.x - p.pubi.xl) +
    (p.privi.y - p.pubi.yl) * (p.privi.y - p.pubi.yl) +
    (p.privi.z - p.pubi.zl) * (p.privi.z - p.pubi.zl);

  //std::cout<<p.privi.x<<","<<p.privi.y<<","<<p.privi.z<<","<<p.pubi.xl<<","<<p.pubi.yl<<","<<p.pubi.zl<<std::endl;
  dist_ln = d2.ConvertToLong();
#ifdef DBG_NCOMM
  std::cout <<dist_ln<<std::endl;
#endif
  diff_dist = p.pubi.radius * p.pubi.radius - dist_ln;

  //  d2 = 0;  // debug
  //  diff_dist = 197*197 + 12*12 + 3*3 + 2; // 38964

  if(diff_dist < 0) {
    std::cout << "**Proof verification FAILED**" << std::endl;
    return -1;
  }

  for(int j=0; j<4; j++) {  // calculate_A1_A2_A3_A4()
    approx = sqrt(diff_dist); // approximation by rounding while assigning to integer
    p.privi.a[j] = approx;
    diff_dist -= approx * approx;
#ifdef DBG_NCOMM
    std::cout << approx << std::endl;
#endif
  }
#ifdef DBG_NCOMM
  std::cout << "distance squared " << d2 << std::endl << std::endl;
#endif
  for(int j=0; j<4; j++) {
    d2 += p.privi.a[j] * p.privi.a[j];
  };
#ifdef DBG_NCOMM
  std::cout << "Recalculated d2 " << d2 << std::endl << std::endl;
  std::cout << "Original d2 " << (p.pubi.radius * p.pubi.radius) << std::endl << std::endl;
#endif
  p.pubi.d2 = d2;
  return d2.ConvertToLong();
}

double Geocoord::get_coord_x(void) {return R() * gradToRad() * (c_latitude - org_latitude); }; // (node - airdrop); X direction is to south pole,
double Geocoord::get_coord_y(void) {return R() * gradToRad() * (org_longitude - c_longitude) * cos(gradToRad() * org_latitude); };  // Y is to Greenwich
double Geocoord::get_coord_z(void) {return (c_elevation - org_elevation);} ;  // Z is up


double convertStringToDouble(string s)
{
    double val;
    stringstream ss;
    ss << s;
    ss >> val;
    return val;
}
#ifdef DBG_NCOMM
bool step_verify(Prover &P) {

  CryptoPP::Integer var1 = CreateCommitment(P.pp, P.rsp.Xn, P.rsp.Yn, P.rsp.Zn, P.rsp.R);

  if(P.pp.group.Multiply(
       var1,
       P.pp.group.MultiplicativeInverse(
    		   P.pp.group.Exponentiate(P.pubi.su, P.c)))
     !=
    		 P.ic.t_n) {
    std::cout << "Trap-location" << std::endl;
    return false;
  }

  CryptoPP::Integer var2 = CreateACommitment(P.pp, P.rsp.R_a, P.rsp.A);

  if(P.pp.group.Multiply(
       var2,
       P.pp.group.MultiplicativeInverse(
    		   P.pp.group.Exponentiate(P.ic.sa, P.c)))
     !=
    		 P.ic.t_a) {
    std::cout << "Trap-squares" << std::endl;
    return false;
  }
  CryptoPP::Integer pwr = P.c * P.c * P.pubi.d2 - (
    (P.rsp.Xn - P.c * P.pubi.xl)*(P.rsp.Xn - P.c * P.pubi.xl) +
    (P.rsp.Yn - P.c * P.pubi.yl)*(P.rsp.Yn - P.c * P.pubi.yl) +
    (P.rsp.Zn - P.c * P.pubi.zl)*(P.rsp.Zn - P.c * P.pubi.zl));
  for(int j=0; j<4; j++)
    pwr -= P.rsp.A[j] * P.rsp.A[j];

  if(CreateNCommitment(P.pp, pwr, P.rsp.R_d)
     !=
    		 P.pp.group.Multiply(
    				 P.pp.group.Exponentiate(P.ic.b_1, P.c),
    				 P.ic.b_0)) {
    std::cout << "Trap-radius" << std::endl;
    return false;
  }

  return true;
}
#endif

string genProof(string s){
	Geocoord gcs;
	Parameters Prm;
	Prover P;
	double xn, yn, zn;
	double xl,yl , zl;
	long RR;
	RandomPool randPool;
	SetParameters(Prm, randPool);

	P.pp = Prm;

	neb::CJsonObject st(s);

	int size = st.GetArraySize();
	cout<<size<<endl;
	if(size < 8){
		return "";
	}
	xn = convertStringToDouble(st[0]("xn"));
	yn = convertStringToDouble(st[1]("yn"));
	zn = convertStringToDouble(st[2]("zn"));
    xl = convertStringToDouble(st[3]("xl"));
    yl = convertStringToDouble(st[4]("yl"));
    zl = convertStringToDouble(st[5]("zl"));
    RR = convertStringToDouble(st[6]("R"));
    P.c = Integer(st[7]("c").c_str());

	gcs.set_origin_DD(xl, yl, zl);
	set_airdrop_location(P, 0, 0, 0, RR);
#ifdef DBG_NCOMM
	printf("%lf,%lf,%lf,%ld,%lf,%lf,%lf\n",xl,yl,zl,RR,xn,yn,zn);
#endif
	gcs.set_coords_DD(xn, yn, zn);
	set_node_location(P, randPool, gcs.get_coord_x(), gcs.get_coord_y(), gcs.get_coord_z());
#ifdef DBG_NCOMM
	std::cout << "Pocket location (degrees) " << xn << ", " << yn << ", " << zn << std::endl;
	std::cout << "(meters) " << gcs.get_coord_x() << ", " << gcs.get_coord_y() << ", " << gcs.get_coord_z() << std::endl;
#endif

	long radius_actual;  // radius after approximation
	radius_actual = get_airdrop_radius(P);
	if(radius_actual == -1){
		return "-1";
	}
	radius_actual = sqrt(radius_actual);  // approximate and calculate "more" witness
	P.step_start(randPool);

	P.step_responses();
//Xn,Yn,Zn,R,Ra,Rd,Sa,c,b1,A[0],A[1],A[2],A[3],xl,yl,zl,Su,d2,pubp,n,gx,gy,gz,g,gr,h[0],h[1],h[2],h[3]
	stringstream ss;
//	string Message;

	neb::CJsonObject oJson("");

	oJson.AddEmptySubObject("response");
	ss << P.rsp.Xn;   oJson["response"].Add("Xn", ss.str());       ss.str("");
	ss << P.rsp.Yn;   oJson["response"].Add("Yn", ss.str());       ss.str("");
	ss << P.rsp.Zn;   oJson["response"].Add("Zn", ss.str());       ss.str("");
	ss << P.rsp.R;    oJson["response"].Add("R", ss.str());        ss.str("");
	ss << P.rsp.R_a;  oJson["response"].Add("R_a", ss.str());      ss.str("");
	ss << P.rsp.R_d;  oJson["response"].Add("R_d", ss.str());      ss.str("");
	ss << P.rsp.A[0]; oJson["response"].Add("A[0]", ss.str());     ss.str("");
	ss << P.rsp.A[1]; oJson["response"].Add("A[1]", ss.str());     ss.str("");
	ss << P.rsp.A[2]; oJson["response"].Add("A[2]", ss.str());     ss.str("");
	ss << P.rsp.A[3]; oJson["response"].Add("A[3]", ss.str());     ss.str("");
	//response
	oJson.AddEmptySubObject("initialCommitments");
	ss << P.ic.sa;    oJson["initialCommitments"].Add("sa", ss.str());     ss.str("");
	ss << P.ic.t_n;   oJson["initialCommitments"].Add("t_n", ss.str());    ss.str("");
	ss << P.ic.t_a;   oJson["initialCommitments"].Add("t_a", ss.str());    ss.str("");
	ss << P.ic.b_1;   oJson["initialCommitments"].Add("b_1", ss.str());    ss.str("");
	ss << P.ic.b_0;   oJson["initialCommitments"].Add("b_0", ss.str());    ss.str("");
	//initialCommitments
	oJson.AddEmptySubObject("challenge");
	ss << P.c;        oJson["challenge"].Add("c", ss.str());      ss.str("");
	//challenge
	oJson.AddEmptySubObject("publicInfo");
	ss << P.pubi.xl;  oJson["publicInfo"].Add("xl", ss.str());    ss.str("");
	ss << P.pubi.yl;  oJson["publicInfo"].Add("yl", ss.str());    ss.str("");
	ss << P.pubi.zl;  oJson["publicInfo"].Add("zl", ss.str());    ss.str("");
	ss << P.pubi.su;  oJson["publicInfo"].Add("su", ss.str());    ss.str("");
	ss << P.pubi.d2;  oJson["publicInfo"].Add("d2", ss.str());    ss.str("");
	//publicInfo

	oJson.AddEmptySubObject("parameters");
	ss << P.pp.n;     oJson["parameters"].Add("n", ss.str());       ss.str("");
	ss << P.pp.gx;    oJson["parameters"].Add("gx", ss.str());      ss.str("");
	ss << P.pp.gy;    oJson["parameters"].Add("gy", ss.str());      ss.str("");
	ss << P.pp.gz;    oJson["parameters"].Add("gz", ss.str());      ss.str("");
	ss << P.pp.g;     oJson["parameters"].Add("g", ss.str());       ss.str("");
	ss << P.pp.gr;    oJson["parameters"].Add("gr", ss.str());      ss.str("");
	ss << P.pp.h[0];  oJson["parameters"].Add("h[0]", ss.str());    ss.str("");
	ss << P.pp.h[1];  oJson["parameters"].Add("h[1]", ss.str());    ss.str("");
	ss << P.pp.h[2];  oJson["parameters"].Add("h[2]", ss.str());    ss.str("");
	ss << P.pp.h[3];  oJson["parameters"].Add("h[3]", ss.str());
	//Parameters
#ifdef DBG_NCOMM
	bool isok = step_verify(P);
	if(isok)
	    std::cout << "**Proof verification SUCCEED**" << std::endl << std::endl << std::endl;
	  else
	    std::cout << "**Proof verification FAILED**" << std::endl << std::endl << std::endl;
#endif
	return oJson.ToString();
}
//int main() {
//	//char* s1 = "[{\"xn\":\"47.1666\"},{\"yn\":\"8.5161\"},{\"zn\":\"425.\"},{\"xl\":\"47.1666\"},{\"yl\":\"8.6166\"},{\"zl\":\"100.\"},{\"R\":\"10000\"},{\"c\":\"121332\"}]";
//	string s1;
//	cin>>s1;
//	cout<<genProof(s1.c_str())<<endl;
//
//  return 0;
//}