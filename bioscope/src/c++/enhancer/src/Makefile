\\CC = g++

CFLAGS = -g -O6 -DDEBUG

BOOST_HOME_DIR=/usr/tar/packages/regex/boost-1.32_0

#INCLUDES =  -I/usr/include/c++/3.2.2 -I/usr/tar/packages/regex/boost_1_32_0 -I../include 

INCLUDES =  -I/usr/include/c++/4.4.1 -I/usr/include/boost -I../include -I/usr/local/include -I/usr/include/c++/4.4.1 -I/usr/include/boost -I../include  -I/usr/include/g++  -I/usr/lib/jvm/java-7-openjdk-amd64/include

#LIBS=/usr/include/boost/libs/regex/build/gcc/libboost_regex.a $(INCDIR)/usr/include/boost/stage/lib/libboost_filesystem.a $(INCDIR)/usr/local/lib/libsequence.a 

LIBS=-lboost_regex -lboost_filesystem -lstdc++ -lboost_system -lsequence  -lz

SHAREDLIBS=/usr/local/lib/libsequence.so /usr/lib/x86_64-linux-gnu/libboost_regex.so /usr/lib/x86_64-linux-gnu/libboost_filesystem.so

LIBPATH=-L/usr/include/boost/libs/regex/build/gcc

#
# compiler options for release build:
#
#C1=-c  -g -fPIC -march=i686 
#-DDEBUG
#
# compiler options for debug build:
#
C1=-c -O6 -fPIC  # -march=i686 

ALL_HEADER= $(BOOST_HOME_DIR)/boost/regex/config.hpp $(BOOST_HOME_DIR)/boost/regex/pattern_except.hpp $(BOOST_HOME_DIR)/boost/regex/regex_traits.hpp $(BOOST_HOME_DIR)/boost/regex/user.hpp $(BOOST_HOME_DIR)/boost/regex/v3/fileiter.hpp $(BOOST_HOME_DIR)/boost/regex/v3/instances.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_compile.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_cstring.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_format.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_kmp.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_library_include.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_match.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_raw_buffer.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_split.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_stack.hpp $(BOOST_HOME_DIR)/boost/regex/v3/regex_synch.hpp

OBJS=SearchPattern.o GetOptions.o EnhancerParam.o BooleanExpression.o EnhancerResults.o AnnotationGrammar.o EnhancerJava.o SequenceClass.o

ENHANCEROBJS=Enhancer.o $(OBJS)

SHAREDOBJS=EnhancerJavaMain.o $(OBJS)
CHECKOBJS=EnhancerCheck.o $(OBJS) 
SUPERSHAREDOBJS=SuperEnhancerJavaMain.o $(OBJS)
SUPERCHECKOBJS=SuperEnhancerCheck.o $(OBJS) 

ENHANCERLIB=libEnhancer.so
CHECKLIB=libECheck.so
SUPERENHANCERLIB=libSuperEnhancer.so
SUPERCHECKLIB=libSuperECheck.so
BIOCISINIT=../../lib/libbiocisinit.a

all : enhancer $(BIOCISINIT) libSuperEnhancer.so libSuperECheck.so libEnhancer.so libECheck.so

enhancer : $(ENHANCEROBJS) 
#	purify --best-effort $(CC) $(LIBPATH) -o enhancer $(ENHANCEROBJS) $(LIBS)
	$(CC) $(LIBPATH) -o enhancer $(ENHANCEROBJS) $(LIBS)

$(BIOCISINIT) : SequenceClass.o AnnotationGrammar.o
	rm -f $(BIOCISINIT)
	ar cr $(BIOCISINIT) SequenceClass.o AnnotationGrammar.o
	ranlib $(BIOCISINIT)

$(ENHANCERLIB) : $(SHAREDOBJS)
	$(CC) -shared $(LIBPATH) -o libEnhancer.so $(SHAREDOBJS) $(SHAREDLIBS)

$(CHECKLIB) : $(CHECKOBJS)
	$(CC) -shared $(LIBPATH) -o libECheck.so $(CHECKOBJS) $(SHAREDLIBS)

$(SUPERENHANCERLIB) : $(SUPERSHAREDOBJS)
	$(CC) -shared $(LIBPATH) -o libSuperEnhancer.so $(SUPERSHAREDOBJS) $(SHAREDLIBS)

$(SUPERCHECKLIB) : $(SUPERCHECKOBJS)
	$(CC) -shared $(LIBPATH) -o libSuperECheck.so $(SUPERCHECKOBJS) $(SHAREDLIBS)

.cc.o:
	$(CC) $(C1) $(INCLUDES) $<

.c.o:
	$(CC) $(C1) -fPIC $(INCLUDES) $<

clean :
	rm -f *.o ; rm -f enhancer ; rm -f ../../lib/libbiocisinit.a

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
  ../include/EnhancerDefines.h ../include/Exon.h
BooleanExpression.o: BooleanExpression.cc
Enhancer.o: Enhancer.cc ../include/EnhancerClass.h \
  ../include/EnhancerParam.h ../include/EnhancerDefines.h \
  ../include/EnhancerGlobalExtern.h
EnhancerCheck.o: EnhancerCheck.cc ../include/EnhancerCheck.h
EnhancerJava.o: EnhancerJava.cc ../include/EnhancerClass.h \
  ../include/EnhancerParam.h ../include/EnhancerDefines.h \
  ../include/EnhancerResult.h ../include/EnhancerFunc.h \
  ../include/EnhancerResults.h ../include/Annotation.h ../include/Exon.h \
  ../include/EnhancerExtern.h
EnhancerJavaMain.o: EnhancerJavaMain.cc ../include/Enhancer.h
EnhancerJavaSave.o: EnhancerJavaSave.cc ../include/EnhancerClass.h \
  ../include/EnhancerParam.h ../include/EnhancerDefines.h \
  ../include/EnhancerResult.h ../include/EnhancerFunc.h \
  ../include/EnhancerResults.h ../include/Annotation.h ../include/Exon.h \
  ../include/EnhancerExtern.h
EnhancerParam.o: EnhancerParam.cc ../include/XmlDefines.h \
  ../include/SeqCodeDefines.h ../include/EnhancerParam.h \
  ../include/EnhancerDefines.h ../include/EnhancerClass.h
EnhancerResults.o: EnhancerResults.cc ../include/EnhancerResults.h \
  ../include/EnhancerResult.h ../include/EnhancerParam.h \
  ../include/EnhancerDefines.h ../include/Annotation.h ../include/Exon.h \
  ../include/WebServer.h
FaReader.o: FaReader.cc
FaReaderGff.o: FaReaderGff.cc
GetOptions.o: GetOptions.cc ../include/EnhancerParam.h \
  ../include/EnhancerDefines.h
gff.o: gff.cc
SearchPattern.o: SearchPattern.cc ../include/EnhancerClass.h \
  ../include/EnhancerResult.h ../include/EnhancerResults.h \
  ../include/EnhancerParam.h ../include/EnhancerDefines.h \
  ../include/Annotation.h ../include/Exon.h
SequenceClass.o: SequenceClass.cc ../include/SequenceClass.h \
  ../include/EnhancerDefines.h ../include/Annotation.h ../include/Exon.h
SuperEnhancerCheck.o: SuperEnhancerCheck.cc \
  ../include/com_biocis_utils_SuperEnhancerCheck.h
SuperEnhancerJavaMain.o: SuperEnhancerJavaMain.cc \
  ../include/com_biocis_utils_SuperEnhancer.h
test.o: test.cc
AnnotationGrammar.o: AnnotationGrammar.cc ../include/Annotation.h \
  ../include/EnhancerDefines.h ../include/Exon.h
BooleanExpression.o: BooleanExpression.cc
Enhancer.o: Enhancer.cc ../include/EnhancerClass.h \
  ../include/EnhancerParam.h ../include/EnhancerDefines.h \
  ../include/EnhancerGlobalExtern.h
EnhancerCheck.o: EnhancerCheck.cc ../include/EnhancerCheck.h
EnhancerJava.o: EnhancerJava.cc ../include/EnhancerClass.h \
  ../include/EnhancerParam.h ../include/EnhancerDefines.h \
  ../include/EnhancerResult.h ../include/EnhancerFunc.h \
  ../include/EnhancerResults.h ../include/Annotation.h ../include/Exon.h \
  ../include/EnhancerExtern.h
EnhancerJavaMain.o: EnhancerJavaMain.cc ../include/Enhancer.h
EnhancerJavaSave.o: EnhancerJavaSave.cc ../include/EnhancerClass.h \
  ../include/EnhancerParam.h ../include/EnhancerDefines.h \
  ../include/EnhancerResult.h ../include/EnhancerFunc.h \
  ../include/EnhancerResults.h ../include/Annotation.h ../include/Exon.h \
  ../include/EnhancerExtern.h
EnhancerParam.o: EnhancerParam.cc ../include/XmlDefines.h \
  ../include/SeqCodeDefines.h ../include/EnhancerParam.h \
  ../include/EnhancerDefines.h ../include/EnhancerClass.h
EnhancerResults.o: EnhancerResults.cc ../include/EnhancerResults.h \
  ../include/EnhancerResult.h ../include/EnhancerParam.h \
  ../include/EnhancerDefines.h ../include/Annotation.h ../include/Exon.h \
  ../include/WebServer.h
FaReader.o: FaReader.cc
FaReaderGff.o: FaReaderGff.cc
GetOptions.o: GetOptions.cc ../include/EnhancerParam.h \
  ../include/EnhancerDefines.h
gff.o: gff.cc
SearchPattern.o: SearchPattern.cc ../include/EnhancerClass.h \
  ../include/EnhancerResult.h ../include/EnhancerResults.h \
  ../include/EnhancerParam.h ../include/EnhancerDefines.h \
  ../include/Annotation.h ../include/Exon.h
SequenceClass.o: SequenceClass.cc ../include/SequenceClass.h \
  ../include/EnhancerDefines.h ../include/Annotation.h ../include/Exon.h
SuperEnhancerCheck.o: SuperEnhancerCheck.cc \
  ../include/com_biocis_utils_SuperEnhancerCheck.h
SuperEnhancerJavaMain.o: SuperEnhancerJavaMain.cc \
  ../include/com_biocis_utils_SuperEnhancer.h
test.o: test.cc
