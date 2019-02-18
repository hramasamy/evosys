#include <string>
#include <functional>
#include <cctype>

namespace Sequence
{
  struct lt_nocase : public std::binary_function<char, char, bool> 
  /*
    Fast, clean, and slightly incorrect implementations of case-insensitive
    character comparisons
    \warning not locale-aware!
   */
  { 
    bool operator()(char x, char y) const 
    { 
      return std::toupper(static_cast<unsigned char>(x)) 
	< std::toupper(static_cast<unsigned char>(y)); 
    } 
  }; 
}
