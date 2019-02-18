//exceptions that can be thrown by Sequence
#ifndef SEQEXCEPTIONS_H
#define SEQEXCEPTIONS_H

#include <iosfwd>

/*! \file SeqExceptions.hpp
  @brief  declaration of exception classes used in namespace Sequence, including class Sequence::SeqException
*/

/*!
  \class Sequence::SeqException Sequence/SeqExceptions.hpp
  All exceptions thrown by routines defined in namespace Sequence are
  derived from a common base class, Sequence::SeqException.
  This class contains a private member, message, which is a C-style
  string.  message is use to store and report error messages when
  exceptions need to be thrown.  The member function SeqException::print
  takes a reference to an ostream as an argument, an can be used to 
  display message. The output operator<< can also be used on objects of this type.
  Alternately, if you need to display the message in some
  other fashion, the public member function SeqException::error 
  will return message to you as a const char *
 
  @short Base class for exceptions that may be thrown
*/

namespace Sequence
  {
  class SeqException
    {
    private:
      const char *message;
    public:
      SeqException (void);
      explicit SeqException (const char *x);
      virtual ~SeqException(void);
      std::ostream & print (std::ostream & out);
      std::ostream & print (std::ostream & out) const;
      const char *error (void) const;
    };

    class badFormat:public SeqException
    /*!
      used largely by I/O routines when input is not in the correct format
    */
    {
    public:
      ~badFormat(void);
      explicit badFormat (const char * x);
    };

  inline std::ostream &
  operator<< (std::ostream & s, const SeqException & c)
  /*!
    \ingroup operators
    Allows objects derived from Sequence::SeqException
    to be written to output streams.  This operator
    acts by a call to the funtion Sequence::SeqException::print
  */
  {
    return c.print (s);
  }

}
#endif
