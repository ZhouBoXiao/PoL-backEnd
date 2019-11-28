d:
	g++ -shared -fPIC -g -o aNativeLib.so NativeLib.cpp  genProofFun.cpp CJsonObject.cpp cJSON.c  -D_LINUX_OS_ -I /usr/java/jdk1.8.0_181-amd64/include -I /usr/java/jdk1.8.0_181-amd64/include/linux  -L /usr/local/lib libcryptopp.a


App := oNativeLib.so
App_Cpp_Flags := -D_LINUX_OS_ -I /usr/java/jdk1.8.0_181-amd64/include -I /usr/java/jdk1.8.0_181-amd64/include/linux
App_Link_Flags := -L /usr/local/lib libcryptopp.a

a:
	g++ -shared -fPIC -o $(App) NativeLib.cpp  genProofFun.cpp CJsonObject.cpp cJSON.c $(App_Cpp_Flags) $(App_Link_Flags)
	cp $(App)   ../resources/jniLibs

t:
	g++   -O2 -Wall -ggdb -m64 -D_GNU_SOURCE=1 -D_REENTRANT -D__GUNC__  -c -o genProofFun.o genProofFun.cpp  -D_LINUX_OS_
	g++ -g -o genProofFun genProofFun.o CJsonObject.cpp cJSON.c  -D_LINUX_OS_ -L /usr/local/lib libcryptopp.a
