#
# Nice Stemmer Makefile
#    Kiduk Yang, 4/30/98
#

DEFINE = define.h display.h class.h string.h
FINDW = findw.h findw2.h 
STEMMERS = simple.h pporter.h porter.h krovetz.h combo.h

all: nstem nstemb makelist makelist2 makelists combo libstemming.so
INC=-I/usr/include/g++
CC=g++ -Wno-deprecated -O3

nstem: $(DEFINE) $(FINDW) $(STEMMERS) findw0.h makelist0.h nstem.cc
	$(CC) -o nstem $(INC) nstem.cc

nstemb: $(DEFINE) $(FINDW) $(STEMMERS) hash.h lists.h nstem.cc
	$(CC) -o nstemb $(INC) nstemb.cc

libstemming.so: $(DEFINE) $(FINDW) $(STEMMERS) hash.h lists.h nstem.cc
	$(CC) -o libstemming.so -shared -fPIC  $(INC) stemming.cc

makelist: findw.h makelist.h makelist.cc
	$(CC) -o makelist $(INC) makelist.cc

makelist2: string.h findw2.h makelist2.h makelist2.cc
	$(CC) -o makelist2 $(INC) makelist2.cc

makelists: string.h findw2.h makelist.h makelist2.h makelists.cc
	$(CC) -o makelists $(INC) makelists.cc

combo: $(FINDW) string.h class.h simple.h pporter.h porter.h krovetz.h
	$(CC) -o combo $(INC) combo.cc
