#include "stdio.h"

int main() {
     int a = 1 + 2 * 3;
     int num1 = ((5 + 5 * 4) + (5 * 5 + 25)) / 3;
     int num2 = (1 + 2 - 3 * 5 / 6) + (7 - 8 * 9) / 10;
     int num3 = num1 + num2;
     int x = 2;

     if (a + x < (a * x - x)) {
         x = 1;
     }
     else if(a <10){
         x = 10;
     }
     else if (num3 < 100){
         x = 100;
     }else {
         x =1000;
     }
     int count = 0;
     int max = 10000;
     int num = 1;
     int i = 2;
     int j = 0;
     int k = 0;
     while (num <= max) {
         j = 0;
         i = 2;
         while (i < num) {
             k = num ;
             while (k >= i) {
                 k = k - i;
             }
             if (k == 0) {
                 j = j + 1;
                 i = num;
             }
             i = i + 1;
         }
         if (j == 0) {
             count = count + 1;
         }
         num = num + 1;
     }
}