#include <Sequence/SimParams.hpp>
#include <Sequence/Portability/StringStreams.hpp>
#include <iostream>
#include <cctype>

namespace Sequence
{
  SimParams::SimParams (void):_command_line(""),_howmany(0),tsam(0)
  {}

  int SimParams::fromfile ( FILE * openfile )
  {
    _command_line.clear();
    _command_line.reserve(100);
    char ch;
    while( (ch=std::fgetc(openfile)) != EOF )
      {
	if (ch == '\n')
	  break;
	else
	  _command_line += ch;
      }
    //some versions of ms output the values with
    //which the RNG was seeded.  This deals with those cases
    while( (ch=std::fgetc(openfile)) != EOF )
      {
        if ((!isdigit(int(ch))&&!isspace(int(ch)))|| ch=='\n')
	  break;
      }
    istr in(_command_line.c_str());
    std::string ms;//for the program name...
    in >> ms >> tsam >> _howmany;
    return int(ch);
  }

  std::istream & SimParams::read (std::istream & s)
    /*!
      reads in the data from a istream
    */
  {
    _command_line.clear();
    char ch;
    //read in the command line
    while (s.get(ch))
      {
        if (ch == '\n')
          {
            break;
          }
        else
          {
            _command_line += ch;
          }
      }

    //some versions of ms output the values with
    //which the RNG was seeded.  This deals with those cases
    //    std::string seedline;
    while (1)
      {
        s.get (ch);
        if ((!isdigit(int(ch))&&!isspace(int(ch)))|| ch=='\n')
          break;
	//        else
	//          seedline += ch;
      }

    //open a stringstream to read the sample size and # runs
    //from the command-line args
    istr in(_command_line.c_str());
    std::string ms;//for the program name...
    in >> ms >> tsam >> _howmany;
    return s;
  }

  std::ostream & operator<< (std::ostream & stream, class SimParams & object)
    /*!
      \ingroup operators
      Writes the parameters passed to Dick Hudson's coalescent simulation
      program to an output stream.
    */
  {
    stream << object._command_line;
    return (stream);
  }

  std::istream& operator>>(std::istream& s,  SimParams& c)
  /*!
    \ingroup operators
    Allows the simulation parameters from Dick Hudson's
    coalescent simulation program to be read in from streams.
  */
  {
    return c.read (s);
  }
}
