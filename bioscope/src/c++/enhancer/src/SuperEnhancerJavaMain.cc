#include <stdio.h>
#include <string.h>
#include <com_biocis_utils_SuperEnhancer.h>

extern void SetAndGo (char *, char *, char *, bool) ;

JNIEXPORT jint JNICALL Java_com_biocis_utils_SuperEnhancer_PerformSearch
(JNIEnv *env , jobject obj, jstring filename, jstring chromosome, jstring session, jstring cache) 
{

  // Create a new Java string from the char *
  //  const char *str  = env->GetStringUTFChars(filename, 0);

  char e_filename[100], e_chromosome[100], e_session[100] ;
  bool e_cache ;
  
  int tempval ;
  const char *str = env->GetStringUTFChars(filename, 0);
  strcpy (e_filename, str) ;
  //  printf("%s\n", e_filename);
  env->ReleaseStringUTFChars(filename, str);

  str = env->GetStringUTFChars(chromosome, 0);
  strcpy (e_chromosome, str) ;
  //  printf("%s\n", e_chromosome);
  env->ReleaseStringUTFChars(chromosome, str);
    

  str = env->GetStringUTFChars(session, 0);
  strcpy (e_session, str) ;
  //  printf("%s\n", e_session);
  env->ReleaseStringUTFChars(session, str);
    
  str = env->GetStringUTFChars(cache, 0);
  if (str[0] == '1')
    e_cache = false ;
  else 
    e_cache = true ;
  env->ReleaseStringUTFChars(cache, str);

  SetAndGo (e_filename, e_chromosome, e_session, e_cache) ;
    
    return 1 ;
      
 }

