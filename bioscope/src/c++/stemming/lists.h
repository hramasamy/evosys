// ------------------------------------------------------------------------
// Name:     LISTS.H
// Author:   Kiduk Yang, 05/19/97
// ------------------------------------------------------------------------
// Description: linked-list classes
//   1. class strnode -- double linked list element (string)
//   2. class strlist -- double linked list (string)
// ------------------------------------------------------------------------


// single linked list of numbers
// -- list represents doc# & doc-freq of a given term
// -- input has to be processed sequentially in order of doc#
// -- adds elements to the end of list

class node {
    private:
        node *next;
        int dn;
        unsigned short int df;
    public:
        node(int dn, unsigned short int df) { node::dn=dn; node::df=df; }
        node(void) { }
        int dnval(void) { return(dn); }
        int dfval(void) { return(df); }
    friend class list;
};


class list {
    private:
        node first;
        node *last;
    public:
        list(void) { first.next=NULL; last=&first; }
        int out(ofstream &outf);     // output list content to a file
        void  clear(void); 
        void  add(node *newnode);     // add element at the end
};
 

int list::out(ofstream &outf) {
    int freq=0;
    node *current=first.next;
    while (current) {
        outf<<current->dnval()<<" "<<current->dfval()<<endl;
        freq++;
        current=current->next;
    }
    return(freq);
}

void list::clear(void) {
    node *current=first.next;
    node *prior;
    while (current) {
	prior=current;
	current=current->next;
	first.next=current;
	delete prior;
	prior=NULL;
    } 
    first.next=NULL;
    last=&first;
}

void list::add(node *newnode) {
    last->next=newnode;
    last=newnode;
    last->next=NULL;
}



// ordered double-linked list of strings
// -- list represent ordered terms
// -- each node represent a term and doc-freq

class strnode {
    private:
        strnode *next;
        strnode *prior;
	char term[WL];   
	unsigned short int df;
    public:
	strnode(char *term) { 
            strcpy(strnode::term,term); df=1; 
        }
        strnode(void) { }
        char *tval(void) { return(term); }
        void dfinc(void) { df++; }
        unsigned int dfval(void) { return(df); }
    friend class strlist;
};

 
class strlist {
    private:
	strnode first;
    public:
	strlist(void) { first.prior=NULL; first.next=NULL; }
	void clear(void);
	void out(ofstream &seq);
	void insert(strnode *newnode);   // insert element in order
};


void strlist::clear(void) {
    strnode *current=first.next;
    strnode *prior;
    while (current) {
	prior=current;
	current=current->next;
	first.next=current;
	delete prior;
	prior=NULL;
    } 
    first.next=NULL;
}

void strlist::out(ofstream &seq) {
    strnode *current=first.next;
    while (current) {
	seq<<current->tval()<<" "<<current->dfval()<<" 0"<<endl;
	current=current->next;
    }
}

void strlist::insert(strnode *newnode) {
    strnode *current=first.next;
    strnode *prior=&first;
    while (current) {
        if (strcmp(current->tval(),newnode->tval())<0) {
            current=current->next;
            prior=prior->next;
        }
	else break;
    }
    if ((current) && strcmp(current->tval(),newnode->tval())==0) {
	current->dfinc();
	delete newnode;
	newnode=NULL;
    }
    else {
	prior->next=newnode;
	newnode->prior=prior;
	newnode->next=current;
    }
}


 
// ordered double-linked list of strings
// -- list represent ordered terms
// -- each node represent a term 

class strnode2 {
    private:
        strnode2 *next;
        strnode2 *prior;
	char term[WL];   
    public:
	strnode2(char *term) { strcpy(strnode2::term,term); }
        strnode2(void) { }
        char *tval(void) { return(term); }
    friend class strlist2;
};

 
class strlist2 {
    private:
	strnode2 first;
    public:
	strlist2(void) { first.next=NULL; }
	int out(ofstream &tnt,int tn);
	void insert(strnode2 *newnode);   // insert element in order
};


int strlist2::out(ofstream &tnt,int tn) {
    strnode2 *current=first.next;
    while (current) {
	tn++;
	tnt<<tn<<" "<<current->tval()<<endl;
	current=current->next;
    }
    return(tn);
}

void strlist2::insert(strnode2 *newnode) {
    strnode2 *current=first.next;
    strnode2 *prior=&first;
    while (current) {
        if (strcmp(current->tval(),newnode->tval())<0) {
            current=current->next;
            prior=prior->next;
        }
	else break;
    }
    if ((current) && strcmp(current->tval(),newnode->tval())==0) {
	delete newnode;
	newnode=NULL;
    }
    else {
	prior->next=newnode;
	newnode->prior=prior;
	newnode->next=current;
    }
}


class htable {                                                              
    private:                                                                
        int tn;                                                             
        int cycle[AZL];                                                     
        char term[WL];                                                      
    public:                                                                 
        short int flag[MAXHN+1];                                            
        htable(void) {                                                      
            // used in hash function                                        
            cycle[AZL-1]=1;                                                 
            for(int i=AZL-2;i>=0;i--) cycle[i]=AZN*cycle[i+1];              
        }                                                                   
        void insert(char *inword,strlist2 terms[]);                   
        void out(strlist2 terms[],ofstream &tnt);
};                                                                          
                                                                            
void htable::insert(char *inword,strlist2 terms[]) {                  
    strcpy(term,inword);                                                    
    tn=strhash(term,cycle);                                                 
    flag[tn]=1;                                                              
    terms[tn].insert(new strnode2(term));                       
}                                                                            
                                                                             
void htable::out(strlist2 terms[],ofstream &tnt) {     
    int tcnt;                                                           
    tcnt=0;                                                                  
    for(int i=1;i<=MAXHN;i++) {                                              
        if (flag[i]==1) {                                                    
            tcnt=terms[i].out(tnt,tcnt);                               
        }                                                                    
    }                                                                        
}                                                                            

