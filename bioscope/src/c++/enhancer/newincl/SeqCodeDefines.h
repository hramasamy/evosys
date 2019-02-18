#ifndef SEQ_CODE_DEFINES
#define SEQ_CODE_DEFINES 

#define TRIPLET 64 

const char *ComplementCode[] = {

  "T",   // A
  "V",   // B
  "G",   // C
  "H",   // D
  "",    // E
  "",    // F
  "C",   // G
  "D",   // H
  "",    // I
  "",    // J
  "M",   // K
  "",    // L
  "K",   // M
  "N",   // N
  "",    // O
  "",    // P
  "",    // Q
  "Y",   // R
  "S",   // S
  "A",   // T
  "",    // U
  "B",   // V
  "W",   // W 
  "",    // X
  "R",   // Y
  "",    // Z
} ;

const char *TransformCode[] = {

  "A",   // A
  "(C|G|T)",   // B
  "C",   // C
  "(A|G|T)",   // D
  "",    // E
  "",    // F
  "G",   // G
  "(A|C|T)",   // H
  "",    // I
  "",    // J
  "(G|T)",   // K
  "",    // L
  "(A|C)",   // M
  "(A|C|G|T)",    // N
  "",    // O
  "",    // P
  "",    // Q
  "(A|G)",   // R
  "(C|G)",   // S
  "T",   // T
  "",    // U
  "(A|C|G)",   // V
  "(A|T)",   // W 
  "",    // X
  "(C|T)",   // Y
  "",    // Z
} ;

const char *LetterCodes[] = {

  "A",   // A
  "CGT",   // B
  "C",   // C
  "AGT",   // D
  "",    // E
  "",    // F
  "G",   // G
  "ACT",   // H
  "",    // I
  "",    // J
  "GT",   // K
  "",    // L
  "AC",   // M
  "ACGT",    // N
  "",    // O
  "",    // P
  "",    // Q
  "AG",   // R
  "CG",   // S
  "T",   // T
  "",    // U
  "ACG",   // V
  "AT",   // W 
  "",    // X
  "CT",   // Y
  "",    // Z
} ;

const int VarCodes[] = {

  1,   // A
  3,   // B
  1,   // C
  3,   // D
  0,    // E
  0,    // F
  1,   // G
  3,   // H
  0,    // I
  0,    // J
  2,   // K
  0,    // L
  2,   // M
  4,    // N
  0,    // O
  0,    // P
  0,    // Q
  2,   // R
  2,   // S
  1,   // T
  0,    // U
  3,   // V
  2,   // W 
  0,    // X
  2,   // Y
  0,    // Z
} ;


#endif
