
#ifndef GENPROOFFUN_H
#define GENPROOFFUN_H


#include <iostream>
#include <math.h>
#include <unistd.h>
#include <term.h>
#include <string>
#include <cstdio>
#include <sstream>
#include <cryptopp/integer.h>
#include <cryptopp/modarith.h>
#include <cryptopp/rsa.h>
#include <cryptopp/randpool.h>
#include <cryptopp/sha.h>
#include <cryptopp/hex.h>
#include <cryptopp/filters.h>
#include "./CJsonObject.hpp"

using namespace std;
using namespace CryptoPP;

#define ENABLE_PAUSE

#define DBG_NEGEXP

class Geocoord {
public:
  void set_origin_DD(double lat, double lon, double elv) {org_latitude = lat; org_longitude = lon; org_elevation = elv; };
  void set_coords_DD(double lat, double lon, double elv) {c_latitude = lat, c_longitude = lon, c_elevation = elv; };

  double get_coord_x(void);  // in meters, from origin
  double get_coord_y(void);
  double get_coord_z(void);

  double R(void) { return 6371000.; }; // Earth radius, meters
private:
  double c_latitude, c_longitude, c_elevation, // location
   org_latitude, org_longitude, org_elevation; // origin
  double gradToRad(void) { return 0.0175; };
};

class Parameters {
public:
//private:
  Integer n,
    g, gx, gy, gz, gr, h[4];
  ModularArithmetic group;
  int rnd_bitsize_modulus,
      rnd_bitsize_commitment,
      rnd_bitsize_chall, rnd_offset_chall;
};

class PublicInfo {
public:
  void set_center(int x, int y, int z);
  int get_radius_sq();  // temporary, to be replaced with Rabin-Shallit
//private:
  long radius; // requested radius
  Integer xl, yl, zl,
    d2;  // threshold for distance (radius), squared, actual
  Integer su; // commitment to node_location
  Integer pubp;
};

class PrivateInfo {
public:
//private:
  Integer x, y, z,
    r,
    a[4],  // witness to non-negative (Lagrange theorem)
    gamma;
};

class ProofPrivate {
public:
//private:
  Integer
    alpha[4], eta,
    rho_0, rho_1,
    beta_x, beta_y, beta_z, beta_r,
    f_0, f_1;
};

class InitialCommitments {
public:
//private:
  Integer b_0, b_1, sa, t_a, t_n;
};

class Responses {
public:
//private:
  Integer A[4], Xn, Yn, Zn, R, R_a, R_d;//, c, sa, b_1;
};

class Prover {
public:
//  void SetParameters(const Parameters &p);
  void step_start(RandomNumberGenerator &rng);
  void step_challenge();
  void step_responses();
  void step_challenge(CryptoPP::RandomNumberGenerator &rng);
//private:
  PublicInfo pubi;
  PrivateInfo privi;

  // from first step of protocol
  ProofPrivate privpf;
  InitialCommitments ic;

  Integer c;  // challenge
  Responses rsp;
  Parameters pp;
};


string genProof(string str);




#endif

