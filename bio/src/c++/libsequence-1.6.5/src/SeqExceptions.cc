#include <Sequence/SeqExceptions.hpp>
#include <iostream>

namespace Sequence
{
  SeqException::SeqException (void):message("Generic SeqException: error encountered")
    /*!
      Default constructor--will generate a default error message,
      which is not likely to be meaningful
      \note this constructor only exists for the purpose of inheriting from this class
    */
  {
  }
  SeqException::SeqException (const char *x):message(x)
    /*!
      Throw the exception with error message x
    */
  {}
  SeqException::~SeqException(void)
  {}
  std::ostream & SeqException::print (std::ostream & out)
    /*!
      Write the error to out.
    */
  {
    out << message;
    return out;
  }
  std::ostream & SeqException::print (std::ostream & out) const
    /*!
      Write the error to out.
    */
  {
    out << message;
    return out;
  }
  const char* SeqException::error (void) const
    /*!
      Return the error message
      if you want to use it in
      some other fashion then
      printing it to an std::ostream
    */
  {
    return message;
  }
    
  badFormat::~badFormat(void)
  {}
  badFormat::badFormat (const char * x):SeqException(x)
    /*!
      Throw the exception with error message x
    */
  {
    return;
  }
}
