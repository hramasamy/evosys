# Generated automatically from Makefile.in by configure.
#
# Copyright 1999-2000,2004 The Apache Software Foundation.
# 
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
# 
#      http://www.apache.org/licenses/LICENSE-2.0
# 
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
#
#
# $Id: Makefile.in,v 1.15 2004/09/08 13:55:32 peiyongz Exp $
#

###################################################################
#                    IMPORTANT NOTE                               #
###################################################################
# If you are going to do the OS390BATCH build, make sure you have #
# the OS390BATCH environment variable set.                        #
#                                                                 #
#   export OS390BATCH=1                                           #
#                                                                 #
###################################################################

PLATFORM = LINUX
COMPILER = gcc
CXXVER = 
GCC = yes
GXX = yes
CXXFLAGS = -g -O2
CFLAGS = -g -O2
LDFLAGS = 
THREADS = 
EXTRA_LIBS = /usr/local/lib/liblog4cxx.a
PREFIX = /usr/local
XERCESCROOT=/usr/tar/packages/xerces-c-src_2_6_0

LOGLIB=../lib/libbiocislogger.a 

include ${XERCESCROOT}/version.incl
include ../Makefile.incl

EXTRAINC = -I../memcache

APP_NAME=MemConfigParse

OUTDIR= .
OBJS=	./MemConfigParse.o ./MemConfigHandlers.o
SRC=	.
HEADER_FILES=${SRC}/MemConfigParse.hpp ${SRC}/MemConfigHandlers.hpp $(SRC)/Logger.h

## OS390BATCH
ifeq (${OS390BATCH},1)
BATCH_TARGET= "//'${LOADMOD}(${APP_NAME})'"
all:: makedir ${BATCH_TARGET}
else
all:: $(LOGLIB) $(OBJS)
endif

makedir::
	-mkdir -p $(OUTDIR)

$(LOGLIB) : $(OBJS)
	rm -f $(LOGLIB)
	ar cr $(LOGLIB) *.o

$(OUTDIR)/MemConfigParse.o:: ${SRC}/MemConfigParse.cpp ${HEADER_FILES}
	${CC} ${INCLUDES} $(EXTRAINC) ${CMP} -o $(OUTDIR)/MemConfigParse.o ${SRC}/MemConfigParse.cpp

$(OUTDIR)/MemConfigHandlers.o:: ${SRC}/MemConfigHandlers.cpp ${HEADER_FILES}
	${CC} ${INCLUDES} $(EXTRAINC) ${CMP} -o $(OUTDIR)/MemConfigHandlers.o ${SRC}/MemConfigHandlers.cpp

clean::
	rm -f $(LOGLIB) ${OBJS} 

distclean::	clean
	rm -f Makefile
