#include "stdio.h"

int main() {
    int a = 3;
    int b = 4;
    if ( 3< (a * 4 - 5)*b) {
        a = a+2;
        a = a*a+a;
        b = b*2;
    }
    else if(a <10){
        a = a+2;
        a = a*a+a;
        b = b +10;
        if(b > 20){
            if(b>18){
                b = 30;
            }else{
                b = 18;
            }
            a = a+2;
            b = 99;
        }else{
            b = 2* b;
        }
    }else if(a >= 20){
        if(b > 20){
            if(b>18){
                b = 30;
            }else{
                b = 18;
            }
            a = a+2;
            b = 99;
        }else{
            b = 2* b;
        }
    }
}