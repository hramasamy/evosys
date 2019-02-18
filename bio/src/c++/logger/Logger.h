// include log4cxx header files.
#include <log4cxx/logger.h>
#include <log4cxx/basicconfigurator.h>
#include <log4cxx/propertyconfigurator.h>
#include <log4cxx/helpers/exception.h>

using namespace log4cxx;
using namespace log4cxx::helpers;

class BioCisLogger {

  private :
    LoggerPtr logger ;
  
  public : 

  BioCisLogger (char *LogFileName) {
    logger = Logger::getRootLogger() ;
    //       std::cout << "enter here" << std::endl ;
    //       PropertyConfigurator::configure(LogFileName) ;
    //       std::cout << "failure here" << std::endl ;
        BasicConfigurator::configure();
  }

  void EnterLogEntry (char *LogInstance, char *message, int type)
  {
        LoggerPtr logger(Logger::getLogger(LogInstance));
	// LoggerPtr logger(Logger::getRootLogger());
    //    std::cout << "logentry here" << std::endl ;
    switch (type)
      {
      case LOGDEBUG : 
	LOG4CXX_DEBUG(logger, message) ;
	break ;
      case LOGINFO :
	LOG4CXX_INFO (logger, message) ;
	break ;
      case LOGWARN :
	LOG4CXX_WARN (logger, message) ;
	break ;
      case LOGERROR :
	LOG4CXX_ERROR (logger, message) ;
	break ;
      case LOGFATAL :
	LOG4CXX_FATAL (logger, message) ;
	break ;
      }
  }

} ;
    
