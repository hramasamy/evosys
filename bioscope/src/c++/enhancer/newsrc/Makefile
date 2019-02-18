CC = g++

COFLAGS = -O6 -c
CFLAGS = -g -DDEBUG 

BOOST_HOME_DIR=/usr/tar/packages/regex/boost-1.32_0

#INCLUDES =  -I/usr/include/c++/3.2.2 -I/usr/tar/packages/regex/boost_1_32_0 -I../include 

INCLUDES =  -I/home/hariharane/lseq/include -I/usr/include/c++/4.4.1 -I/usr/include/boost -I../newincl -I/usr/local/include -I/usr/include/c++/4.4.1 -I/usr/include/boost -I/usr/include/g++ -I/usr/lib/gcc/x86_64-linux-gnu/4.4.1/include/

#LIBS=/usr/include/boost/libs/regex/build/gcc/libboost_regex.a $(INCDIR)/usr/include/boost/stage/lib/libboost_filesystem.a $(INCDIR)/usr/local/lib/libsequence.a 

LIBS=-lboost_regex -lboost_filesystem -lsequence -lboost_system

SHAREDLIBS=/home/hariharane/lseq/lib/libsequence.so $(INCDIR)/usr/lib/libboost_regex.so $(INCDIR)/usr/lib/libboost_filesystem.so

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

OBJS=GetOptions.o PatternClass.o SpeciesIndex.o

MOTIFSTATOBJS=MotifStat.o $(OBJS)

all : motifstat 

motifstat : $(MOTIFSTATOBJS)
#	purify --best-effort $(CC) $(LIBPATH) -o enhancer $(MOTIFSTATOBJS) $(LIBS)
	$(CC) $(LIBPATH) -L/home/hariharane/lseq/lib -o motifstat $(MOTIFSTATOBJS) $(LIBS)

$(BIOCISINIT) : SequenceClass.o AnnotationGrammar.o
	rm -f $(BIOCISINIT)
	ar cr $(BIOCISINIT) SequenceClass.o AnnotationGrammar.o
	ranlib $(BIOCISINIT)

.cc.o:
	$(CC) $(COFLAGS) $(INCLUDES) $<

.c.o:
	$(CC) $(COFLAGS) -fPIC $(INCLUDES) $<

clean :
	rm -f *.o ; rm -f enhancer ; 

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
GetOptions.o: GetOptions.cc ../newincl/PatternClass.h \
 ../newincl/EnhancerDefines.h
MotifStat.o: MotifStat.cc ../newincl/PatternClass.h \
 ../newincl/EnhancerDefines.h ../newincl/Splitter.h \
 ../newincl/SpeciesIndex.h ../newincl/SeqCodeDefines.h
PatternClass.o: PatternClass.cc ../newincl/XmlDefines.h \
 ../newincl/SeqCodeDefines.h ../newincl/PatternClass.h \
 ../newincl/EnhancerDefines.h
SpeciesIndex.o: SpeciesIndex.cc ../newincl/SpeciesIndex.h \
 ../newincl/SeqCodeDefines.h
Splitter.o: Splitter.cc
valid_dna.o: valid_dna.cc
