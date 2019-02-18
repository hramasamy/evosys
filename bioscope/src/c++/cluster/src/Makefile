CC = g++

CFLAGS = -g -O6 -DDEBUG

#BOOST_HOME_DIR=/usr/tar/packages/regex/boost-1.32_0

#INCLUDES =  -I/usr/include/c++/3.2.2 -I/usr/tar/packages/regex/boost_1_32_0 -I../include 

INCLUDES =  -I/usr/include/c++/4.4.1 -I/usr/include/boost -I../include -I/usr/local/include -I/usr/include/c++/4.4.1 -I/usr/include/boost -I../include  -I/usr/include/g++ -I/usr/lib/gcc/x86_64-linux-gnu/4.4.1/include/  -I/usr/lib/jvm/java-7-openjdk-amd64/include


#LIBS=/usr/include/boost/libs/regex/build/gcc/libboost_regex.a $(INCDIR)/usr/include/boost/stage/lib/libboost_filesystem.a $(INCDIR)/usr/local/lib/libsequence.a 

LIBS=-lboost_regex -lboost_filesystem -lstdc++ -lboost_system -lsequence  -lz
#INCLUDES =  -I/usr/local/gcc333/include/c++/3.3.3 -I/usr/tar/packages/regex/boost_1_32_0 -I../include -I/usr/local/include -I/usr/local/gcc333/include/c++/3.3.3 -I/usr/tar/packages/regex/boost_1_32_0 -I../include  -I/usr/include/g++

#LIBS=/usr/tar/packages/regex/boost_1_32_0/libs/regex/build/gcc/libboost_regex.a $(INCDIR)/usr/tar/packages/regex/boost_1_32_0/stage/lib/libboost_filesystem.a $(INCDIR)/usr/local/lib/libsequence.a 

SHAREDLIBS=/usr/lib/libsequence.so $(INCDIR)/usr/lib/libboost_regex.so $(INCDIR)/usr/lib/libboost_filesystem.so

LIBPATH=-L/usr/include/boost/libs/regex/build/gcc

#
# compiler options for release build:
#
#C1=-c  -g3 -fPIC -march=i686  -DDEBUG
#-DDEBUG
#
# compiler options for debug build:
#
C1=-c -O6 -fPIC  # -march=i686 

ALL_HEADER= $(BOOST_HOME_DIR)/boost/regex/config.hpp $(BOOST_HOME_DIR)/boost/regex/pattern_except.hpp $(BOOST_HOME_DIR)/boost/regex/regex_traits.hpp $(BOOST_HOME_DIR)/boost/regex/user.hpp $(BOOST_HOME_DIR)/boost/regex/v3/fileiter.hpp $(BOOST_HOME_DIR)/boost/regex/v3/instances.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_compile.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_cstring.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_format.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_kmp.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_library_include.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_match.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_raw_buffer.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_split.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_stack.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_synch.hpp

OBJS=GetOptions.o EnhancerParam.o BooleanExpression.o AnnotationGrammar.o EnhancerJava.o ClusterEnhancerClass.o

ENHANCEROBJS=Enhancer.o $(OBJS)

SUPERSHAREDOBJS=SuperClusterJavaMain.o $(OBJS)
SUPERCHECKOBJS=SuperClusterCheck.o $(OBJS) 

SUPERENHANCERLIB=libSuperClusterBundle.so
SUPERCHECKLIB=libClusterECheck.so

all : cluster libSuperClusterBundle.so libClusterECheck.so 

cluster : $(ENHANCEROBJS)
#	purify --best-effort $(CC) $(LIBPATH) -o enhancer $(ENHANCEROBJS) $(LIBS)
	$(CC) $(LIBPATH) -o cluster $(ENHANCEROBJS) $(LIBS)


$(ENHANCERLIB) : $(SHAREDOBJS)
	$(CC) -shared $(LIBPATH) -o libEnhancer.so $(SHAREDOBJS) $(SHAREDLIBS)

$(CHECKLIB) : $(CHECKOBJS)
	$(CC) -shared $(LIBPATH) -o libECheck.so $(CHECKOBJS) $(SHAREDLIBS)

$(SUPERENHANCERLIB) : $(SUPERSHAREDOBJS)
	$(CC) -shared $(LIBPATH) -o libSuperClusterBundle.so $(SUPERSHAREDOBJS) $(SHAREDLIBS)

$(SUPERCHECKLIB) : $(SUPERCHECKOBJS)
	$(CC) -shared $(LIBPATH) -o libClusterECheck.so $(SUPERCHECKOBJS) $(SHAREDLIBS)

.cc.o:
	$(CC) $(C1) $(INCLUDES) $<

.c.o:
	$(CC) $(C1) -fPIC $(INCLUDES) $<

clean :
	rm -f *.o ; rm -f cluster

#   NOT FOR END USERS!
depend:
	cp Makefile Makefile.bak \
	    && sed -ne '1,/^# DO NOT REMOVE/p' Makefile > Makefile.new \
	    && g++ -MM $(INCLUDES) $(CFLAGS) *.cc >> Makefile.new \
	    && sed -e '1,$$s: $(INCDIR)/: $$(INCDIR)/:g' \
		   -e '1,$$s: $(OSDIR)/: $$(OSDIR)/:g' Makefile.new \
		> Makefile \
	    && rm Makefile.new

##
##  (End of automatically generated section)
##
AnnotationGrammar.o: AnnotationGrammar.cc ../include/Annotation.h \
  ../include/EnhancerDefines.h
BooleanExpression.o: BooleanExpression.cc
ClusterEnhancerClass.o: ClusterEnhancerClass.cc \
  ../include/EnhancerClass.h ../include/ClusterEnhancerClass.h \
  ../include/EnhancerParam.h ../include/ClusterMap.h \
  ../include/EnhancerDefines.h ../include/Annotation.h \
  ../include/WebServer.h
Enhancer.o: Enhancer.cc ../include/EnhancerClass.h \
  ../include/EnhancerParam.h ../include/ClusterMap.h \
  ../include/EnhancerDefines.h ../include/EnhancerGlobalExtern.h
EnhancerCheck.o: EnhancerCheck.cc ../include/EnhancerCheck.h
EnhancerJava.o: EnhancerJava.cc ../include/EnhancerClass.h \
  ../include/EnhancerParam.h ../include/ClusterMap.h \
  ../include/EnhancerDefines.h ../include/EnhancerResult.h \
  ../include/EnhancerFunc.h ../include/EnhancerResults.h \
  ../include/Annotation.h ../include/EnhancerExtern.h \
  ../include/ClusterEnhancerClass.h
EnhancerJavaMain.o: EnhancerJavaMain.cc ../include/Enhancer.h
EnhancerJavaSave.o: EnhancerJavaSave.cc ../include/EnhancerClass.h \
  ../include/EnhancerParam.h ../include/ClusterMap.h \
  ../include/EnhancerDefines.h ../include/EnhancerResult.h \
  ../include/EnhancerFunc.h ../include/EnhancerResults.h \
  ../include/Annotation.h ../include/Genes.h ../include/EnhancerExtern.h
EnhancerParam.o: EnhancerParam.cc ../include/XmlDefines.h \
  ../include/SeqCodeDefines.h ../include/EnhancerParam.h \
  ../include/ClusterMap.h ../include/EnhancerDefines.h \
  ../include/EnhancerClass.h
EnhancerResults.o: EnhancerResults.cc ../include/EnhancerResults.h \
  ../include/EnhancerResult.h ../include/EnhancerParam.h \
  ../include/ClusterMap.h ../include/EnhancerDefines.h \
  ../include/Annotation.h ../include/WebServer.h
FaReader.o: FaReader.cc
FaReaderGff.o: FaReaderGff.cc
GetOptions.o: GetOptions.cc ../include/EnhancerParam.h \
  ../include/ClusterMap.h ../include/EnhancerDefines.h
gff.o: gff.cc
SearchPattern.o: SearchPattern.cc ../include/EnhancerClass.h \
  ../include/EnhancerResult.h ../include/EnhancerResults.h \
  ../include/EnhancerParam.h ../include/ClusterMap.h \
  ../include/EnhancerDefines.h ../include/Annotation.h \
  ../include/ClusterEnhancerClass.h
SuperClusterCheck.o: SuperClusterCheck.cc \
  ../include/ClusterEnhancerCheck.h
SuperClusterJavaMain.o: SuperClusterJavaMain.cc \
  ../include/ClusterEnhancer.h
