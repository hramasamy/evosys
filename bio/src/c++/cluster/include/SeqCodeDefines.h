#ifndef SEQ_CODE_DEFINES
#define SEQ_CODE_DEFINES 

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

#endif
