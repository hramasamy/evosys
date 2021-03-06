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
 * $Log: MemConfigParse.cpp,v $
 * Revision 1.18  2004/09/08 13:55:32  peiyongz
 * Apache License Version 2.0
 *
 * Revision 1.17  2004/09/02 14:59:29  cargilld
 * Add OutOfMemoryException block to samples.
 *
 * Revision 1.16  2003/08/07 21:21:38  neilg
 * fix segmentation faults that may arise when the parser throws exceptions during document parsing.  In general, XMLPlatformUtils::Terminate() should not be called from within a catch statement.
 *
 * Revision 1.15  2003/05/30 09:36:35  gareth
 * Use new macros for iostream.h and std:: issues.
 *
 * Revision 1.14  2002/04/17 20:18:08  tng
 * [Bug 7493] The word "occured" is misspelled and it is a global error.
 *
 * Revision 1.13  2002/02/01 22:37:38  peiyongz
 * sane_include
 *
 * Revision 1.12  2001/10/25 15:18:33  tng
 * delete the parser before XMLPlatformUtils::Terminate.
 *
 * Revision 1.11  2001/10/19 18:52:04  tng
 * Since MemConfigParse can take any XML file as input file, it shouldn't hardcode to expect 16 elements.
 * Change it to work similar to SAXCount which just prints the number of elements, characters, attributes ... etc.
 * And other modification for consistent help display and return code across samples.
 *
 * Revision 1.10  2001/08/01 19:11:01  tng
 * Add full schema constraint checking flag to the samples and the parser.
 *
 * Revision 1.9  2001/05/11 13:24:55  tng
 * Copyright update.
 *
 * Revision 1.8  2001/05/03 15:59:48  tng
 * Schema: samples update with schema
 *
 * Revision 1.7  2000/06/20 02:23:08  rahulj
 * Help message added by Joe Polastre.
 *
 * Revision 1.6  2000/03/03 01:29:31  roddey
 * Added a scanReset()/parseReset() method to the scanner and
 * parsers, to allow for reset after early exit from a progressive parse.
 * Added calls to new Terminate() call to all of the samples. Improved
 * documentation in SAX and DOM parsers.
 *
 * Revision 1.5  2000/03/02 19:53:44  roddey
 * This checkin includes many changes done while waiting for the
 * 1.1.0 code to be finished. I can't list them all here, but a list is
 * available elsewhere.
 *
 * Revision 1.4  2000/02/11 02:37:48  abagchi
 * Removed StrX::transcode
 *
 * Revision 1.3  2000/02/06 07:47:20  rahulj
 * Year 2K copyright swat.
 *
 * Revision 1.2  2000/01/12 00:27:00  roddey
 * Updates to work with the new URL and input source scheme.
 *
 * Revision 1.1.1.1  1999/11/09 01:09:45  twl
 * Initial checkin
 *
 * Revision 1.5  1999/11/08 20:43:38  rahul
 * Swat for adding in Product name and CVS comment log variable.
 *
 */


// ---------------------------------------------------------------------------
//  This sample program demonstrates the progressive parse capabilities of
//  the parser system. It allows you to do a scanFirst() call followed by
//  a loop which calls scanNext(). You can drop out when you've found what
//  ever it is you want. In our little test, our event handler looks for
//  16 new elements then sets a flag to indicate its found what it wants.
//  At that point, our progressive parse loop below exits.
//
//  The parameters are:
//
//      [-?]            - Show usage and exit
//      [-v=xxx]        - Validation scheme [always | never | auto*]
//      [-n]            - Enable namespace processing
//      [-s]            - Enable schema processing
//      [-f]            - Enable full schema constraint checking
//      filename        - The path to the XML file to parse
//
//  * = Default if not provided explicitly
//  These are non-case sensitive
// ---------------------------------------------------------------------------


// ---------------------------------------------------------------------------
//  Includes
// ---------------------------------------------------------------------------
extern "C" int LoggerInitialize (char *) ;
#include <xercesc/util/PlatformUtils.hpp>
#include <xercesc/framework/XMLPScanToken.hpp>
#include <xercesc/parsers/SAXParser.hpp>
#include "MemConfigParse.hpp"
#include <xercesc/util/OutOfMemoryException.hpp>
#include <iostream>

// ---------------------------------------------------------------------------
//  Local data
//
//  xmlFile
//      The path to the file to parser. Set via command line.
//
//  doNamespaces
//      Indicates whether namespace processing should be done.
//
//  doSchema
//      Indicates whether schema processing should be done.
//
//  schemaFullChecking
//      Indicates whether full schema constraint checking should be done.
//
//  valScheme
//      Indicates what validation scheme to use. It defaults to 'auto', but
//      can be set via the -v= command.
// ---------------------------------------------------------------------------
static char*	 xmlFile         = 0;
static bool     doNamespaces       = false;
static bool     doSchema           = false;
static bool     schemaFullChecking = false;
static SAXParser::ValSchemes    valScheme       = SAXParser::Val_Auto;

// ---------------------------------------------------------------------------
//  Local helper methods
// ---------------------------------------------------------------------------
static void usage()
{
    XERCES_STD_QUALIFIER cout << "\nUsage:\n"
            "    MemConfigParse [options] <XML file>\n\n"
            "This program demonstrates the progressive parse capabilities of\n"
	         "the parser system. It allows you to do a scanFirst() call followed by\n"
            "a loop which calls scanNext(). You can drop out when you've found what\n"
            "ever it is you want. In our little test, our event handler looks for\n"
            "16 new elements then sets a flag to indicate its found what it wants.\n"
            "At that point, our progressive parse loop exits.\n\n"
            "Options:\n"
            "      -v=xxx        - Validation scheme [always | never | auto*].\n"
            "      -n            - Enable namespace processing [default is off].\n"
            "      -s            - Enable schema processing [default is off].\n"
            "      -f            - Enable full schema constraint checking [default is off].\n"
            "      -?            - Show this help.\n\n"
            "  * = Default if not provided explicitly.\n"
         <<  XERCES_STD_QUALIFIER endl;
}



int LoggerInitialize (char *configfile) 
{
    // Initialize the XML4C system
    try
    {
         XMLPlatformUtils::Initialize();
    }

    catch (const XMLException& toCatch)
    {
         XERCES_STD_QUALIFIER cerr << "Error during initialization! :\n"
              << StrX(toCatch.getMessage()) << XERCES_STD_QUALIFIER endl;
         return 1;
    }

    xmlFile = configfile ; 
    int errorCount = 0;
    //
    //  Create a SAX parser object to use and create our SAX event handlers
    //  and plug them in.
    //
    SAXParser* parser = new SAXParser;
    MemConfigHandlers handler;
    parser->setDocumentHandler(&handler);
    parser->setErrorHandler(&handler);
    parser->setValidationScheme(valScheme);
    parser->setDoNamespaces(doNamespaces);
    parser->setDoSchema(doSchema);
    parser->setValidationSchemaFullChecking(schemaFullChecking);

    //
    //  Ok, lets do the progressive parse loop. On each time around the
    //  loop, we look and see if the handler has found what its looking
    //  for. When it does, we fall out then.
    //
    unsigned long duration;
    int errorCode = 0;
    try
    {
        // Create a progressive scan token
        XMLPScanToken token;

        const unsigned long startMillis = XMLPlatformUtils::getCurrentMillis();
        if (!parser->parseFirst(xmlFile, token))
        {
            XERCES_STD_QUALIFIER cerr << "scanFirst() failed\n" << XERCES_STD_QUALIFIER endl;
            XMLPlatformUtils::Terminate();
	    return 1;
        }

        //
        //  We started ok, so lets call scanNext() until we find what we want
        //  or hit the end.
        //
        bool gotMore = true;
        while (gotMore && !parser->getErrorCount())
            gotMore = parser->parseNext(token);

        const unsigned long endMillis = XMLPlatformUtils::getCurrentMillis();
        duration = endMillis - startMillis;

        errorCount = parser->getErrorCount();
        //
        //  Reset the parser-> In this simple progrma, since we just exit
        //  now, its not technically required. But, in programs which
        //  would remain open, you should reset after a progressive parse
        //  in case you broke out before the end of the file. This insures
        //  that all opened files, sockets, etc... are closed.
        //
        parser->parseReset(token);
    }
    catch (const OutOfMemoryException&)
    {
        XERCES_STD_QUALIFIER cerr << "OutOfMemoryException" << XERCES_STD_QUALIFIER endl;
        errorCode = 5;
    }
    catch (const XMLException& toCatch)
    {
        XERCES_STD_QUALIFIER cerr << "\nAn error occurred: '" << xmlFile << "'\n"
             << "Exception message is: \n"
             << StrX(toCatch.getMessage())
             << "\n" << XERCES_STD_QUALIFIER endl;
        errorCode = 4;
    }

    if(errorCode) {
        XMLPlatformUtils::Terminate();
        return errorCode;
    }

    if (!errorCount) {
        XERCES_STD_QUALIFIER cout << xmlFile << ": " << duration << " ms ("
            << handler.getElementCount() << " elems, "
            << handler.getAttrCount() << " attrs, "
            << handler.getSpaceCount() << " spaces, "
            << handler.getCharacterCount() << " chars)" << XERCES_STD_QUALIFIER endl;
    }

    //
    //  Delete the parser itself.  Must be done prior to calling Terminate, below.
    //
    delete parser;

    // And call the termination method
    XMLPlatformUtils::Terminate();

    if (errorCount > 0)
        return 4;
    else
        return 0;
}

