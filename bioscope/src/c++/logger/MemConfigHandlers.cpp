/*
 * Copyright 1999-2001,2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * $Id: MemConfigHandlers.cpp,v 1.8 2004/09/08 13:55:32 peiyongz Exp $
 */



// ---------------------------------------------------------------------------
//  Includes
// ---------------------------------------------------------------------------
#include <xercesc/util/XMLUniDefs.hpp>
#include <xercesc/util/XMLUni.hpp>
#include <xercesc/sax/AttributeList.hpp>
#include "MemConfigDefines.h"
#include "MemConfigParse.hpp"
#include "LoggerDefines.h"
#include "Logger.h" 


BioCisLogger Log ("properties") ;
extern "C" void SetSettings (char *value, int) ;
// ---------------------------------------------------------------------------
//  MemConfigHandlers: Constructors and Destructor
// ---------------------------------------------------------------------------
MemConfigHandlers::MemConfigHandlers() :

    fElementCount(0)
    , fAttrCount(0)
    , fCharacterCount(0)
    , fSpaceCount(0)
{
}

MemConfigHandlers::~MemConfigHandlers()
{
}


// ---------------------------------------------------------------------------
//  MemConfigHandlers: Overrides of the SAX DocumentHandler interface
// ---------------------------------------------------------------------------
void MemConfigHandlers::startElement(const   XMLCh* const    name
                                    ,       AttributeList&  attributes)
{
  char *elename = XMLString::transcode (name) ;
  setconfigstate (elename) ;
  XMLString::release (&elename) ;
    fElementCount++;
    fAttrCount += attributes.getLength();
}

void MemConfigHandlers::characters(  const   XMLCh* const    chars,
				      const unsigned int    length)
{
 
  char *elename = XMLString::transcode (chars) ;
  state = HELP ;
  SetSettings (elename, state) ;
  XMLString::release (&elename) ;
    fCharacterCount += length;
}

void MemConfigHandlers::setconfigstate (char *name)
{
  Log.EnterLogEntry ("MemConfig", name, LOGDEBUG) ;
  state = HELP ;
  if (strcasecmp (name, "port") == 0 )
    {
      state = PORT ;
      return ;
    }

  if (strcasecmp (name, "organism") == 0 )
    {
      state = ORGANISM ;
      return ;
    }

  if (strcasecmp (name, "cachedir") == 0 )
    {
      state = CACHEDIR ;
      return ;
    }

  if (strcasecmp (name, "datadir") == 0 )
    {
      state = DATADIR ;
      return ;
    }

  if (strcasecmp (name, "maxmemory") == 0 )
    {
      state = MAXMEMORY ;
      return ;
    }

  if (strcasecmp (name, "evicttofree") == 0 )
    {
      state = EVICTTOFREE ;
      return ;
    }

  if (strcasecmp (name, "maxcon") == 0 )
    {
      state = MAXCONS ;
      return ;
    }

  if (strcasecmp (name, "lock") == 0 )
    {
      state = LOCKMEMORY ;
      return ;
    }

  if (strcasecmp (name, "verbose") == 0 )
    {
      state = VERBOSE ;
      return ;
    }

  if (strcasecmp (name, "daemonize") == 0 )
    {
      state = DAEMONIZE ;
      return ;
    }

  if (strcasecmp (name, "maxcore") == 0 )
    {
      state = MAXCORE ;
      return ;
    }

  if (strcasecmp (name, "user") == 0 )
    {
      state = USER ;
      return ;
    }

  state = HELP ;

}


  

void MemConfigHandlers::ignorableWhitespace( const   XMLCh* const chars
										    , const unsigned int length)
{
    fSpaceCount += length;
}

void MemConfigHandlers::resetDocument()
{
    fAttrCount = 0;
    fCharacterCount = 0;
    fElementCount = 0;
    fSpaceCount = 0;
}


// ---------------------------------------------------------------------------
//  MemConfigHandlers: Overrides of the SAX ErrorHandler interface
// ---------------------------------------------------------------------------
void MemConfigHandlers::error(const SAXParseException& e)
{
    XERCES_STD_QUALIFIER cerr << "\nError at file " << StrX(e.getSystemId())
		 << ", line " << e.getLineNumber()
		 << ", char " << e.getColumnNumber()
         << "\n  Message: " << StrX(e.getMessage()) << XERCES_STD_QUALIFIER endl;
}

void MemConfigHandlers::fatalError(const SAXParseException& e)
{
    XERCES_STD_QUALIFIER cerr << "\nFatal Error at file " << StrX(e.getSystemId())
		 << ", line " << e.getLineNumber()
		 << ", char " << e.getColumnNumber()
         << "\n  Message: " << StrX(e.getMessage()) << XERCES_STD_QUALIFIER endl;
}

void MemConfigHandlers::warning(const SAXParseException& e)
{
    XERCES_STD_QUALIFIER cerr << "\nWarning at file " << StrX(e.getSystemId())
		 << ", line " << e.getLineNumber()
		 << ", char " << e.getColumnNumber()
         << "\n  Message: " << StrX(e.getMessage()) << XERCES_STD_QUALIFIER endl;
}

