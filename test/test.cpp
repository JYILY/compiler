#include <iostream>
using namespace std;

void test1(){
    int num = (((55 + 5) * 4-(7+8)*4) + ((5 * (5 + 25))*3-8*4)) / 3 ;
    cout<<"answer : "<<num<<endl;

    //翻译结果
    int D0 , D1 , D2 , D3 , D4 , D5 , D6 , D7 , D8 , D9 , D10 , D11 = 0;
    int num1 = 0;
    L0000: D0 = 55 + 5;
    L0001: D1 = D0 * 4;
    L0002: D2 = 7 + 8;
    L0003: D3 = D2 * 4;
    L0004: D4 = D1 - D3;
    L0005: D5 = 5 + 25;
    L0006: D6 = 5 * D5;
    L0007: D7 = D6 * 3;
    L0008: D8 = 8 * 4;
    L0009: D9 = D7 - D8;
    L0010: D10 = D4 + D9;
    L0011: D11 = D10 / 3;
    L0012: num1 = D11;
    L0013: cout<<"compiled : "<<num1<<endl;
}

void test2(){
    int aa = 3;
    int bb = 4;
    if ( 3< (aa * 4 - 5)*bb) {
        aa = aa+2;
        aa = aa*aa+aa;
        bb = bb*2;
    }
    else if(aa <10){
        aa = aa+2;
        aa = aa*aa+aa;
        bb = bb +10;
        if(bb > 20){
            if(bb>18){
                bb = 30;
            }else{
                bb = 18;
            }
            aa = aa+2;
            bb = 99;
        }else{
            bb = 2* bb;
        }
    }else if(aa >= 20){
        if(bb > 20){
            if(bb>18){
                bb = 30;
            }else{
                bb = 18;
            }
            aa = aa+2;
            bb = 99;
        }else{
            bb = 2* bb;
        }
    }
    cout<<" answer a : "<<aa<<endl;
    cout<<" answer b : "<<bb<<endl;

    //翻译结果
    int D0 , D1 , D2 , D3 , D4 , D5 , D6 , D7 , D8 , D9 , D10 , D11 , D12 , D13 , D14 = 0;
    int a , b = 0;
    L0000: a = 3;
    L0001: b = 4;
    L0002: D0 = a * 4;
    L0003: D1 = D0 - 5;
    L0004: D2 = D1 * b;
    L0005: if(3 >= D2) goto L0014;
    L0006: D3 = a + 2;
    L0007: a = D3;
    L0008: D4 = a * a;
    L0009: D5 = D4 + a;
    L0010: a = D5;
    L0011: D6 = b * 2;
    L0012: b = D6;
    L0013: goto L0047;
    L0014: if(a >= 10) goto L0034;
    L0015: D7 = a + 2;
    L0016: a = D7;
    L0017: D8 = a * a;
    L0018: D9 = D8 + a;
    L0019: a = D9;
    L0020: D10 = b + 10;
    L0021: b = D10;
    L0022: if(b <= 20) goto L0031;
    L0023: if(b <= 18) goto L0026;
    L0024: b = 30;
    L0025: goto L0027;
    L0026: b = 18;
    L0027: D11 = a + 2;
    L0028: a = D11;
    L0029: b = 99;
    L0030: goto L0033;
    L0031: D12 = 2 * b;
    L0032: b = D12;
    L0033: goto L0047;
    L0034: if(a < 20) goto L0047;
    L0035: if(b <= 20) goto L0044;
    L0036: if(b <= 18) goto L0039;
    L0037: b = 30;
    L0038: goto L0040;
    L0039: b = 18;
    L0040: D13 = a + 2;
    L0041: a = D13;
    L0042: b = 99;
    L0043: goto L0046;
    L0044: D14 = 2 * b;
    L0045: b = D14;
    L0046: ;
    L0047: ;
    cout<<"compiled a : "<<a<<endl;
    cout<<"compiled b : "<<b<<endl;
 }

 void test3_s(){

     int D0 , D1 , D2 , D3 , D4 , D5 , D6 , D7 , D8 , D9 , D10 , D11 , D12 , D13 , D14 , D15 , D16 , D17 , D18 , D19 , D20 , D21 , D22 , D23 , D24 = 0;
     int a , num1 , num2 , num3 , x , count , max , num , i , j , k = 0;
     L0000: D0 = 2 * 3;
     L0001: D1 = 1 + D0;
     L0002: a = D1;
     L0003: D2 = 5 * 4;
     L0004: D3 = 5 + D2;
     L0005: D4 = 5 * 5;
     L0006: D5 = D4 + 25;
     L0007: D6 = D3 + D5;
     L0008: D7 = D6 / 3;
     L0009: num1 = D7;
     L0010: D8 = 1 + 2;
     L0011: D9 = 3 * 5;
     L0012: D10 = D9 / 6;
     L0013: D11 = D8 - D10;
     L0014: D12 = 8 * 9;
     L0015: D13 = 7 - D12;
     L0016: D14 = D13 / 10;
     L0017: D15 = D11 + D14;
     L0018: num2 = D15;
     L0019: D16 = num1 + num2;
     L0020: num3 = D16;
     L0021: x = 2;
     L0022: D17 = a + x;
     L0023: D18 = a * x;
     L0024: D19 = D18 - x;
     L0025: if(D17 >= D19) goto L0028;
     L0026: x = 1;
     L0027: goto L0035;
     L0028: if(a >= 10) goto L0031;
     L0029: x = 10;
     L0030: goto L0035;
     L0031: if(num3 >= 100) goto L0034;
     L0032: x = 100;
     L0033: goto L0035;
     L0034: x = 1000;
     L0035: count = 0;
     L0036: max = 10000;
     L0037: num = 1;
     L0038: i = 2;
     L0039: j = 0;
     L0040: k = 0;
     L0041: if(num > max) goto L0065;
     L0042: j = 0;
     L0043: i = 2;
     L0044: if(i >= num) goto L0058;
     L0045: k = num;
     L0046: if(k < i) goto L0050;
     L0047: D20 = k - i;
     L0048: k = D20;
     L0049: goto L0046;
     L0050: if(k != 0) goto L0055;
     L0051: D21 = j + 1;
     L0052: j = D21;
     L0053: i = num;
     L0054: ;
     L0055: D22 = i + 1;
     L0056: i = D22;
     L0057: goto L0044;
     L0058: if(j != 0) goto L0062;
     L0059: D23 = count + 1;
     L0060: count = D23;
     L0061: ;
     L0062: D24 = num + 1;
     L0063: num = D24;
     L0064: goto L0041;
     L0065: ;

     cout<<"compiled a : "<<a<<endl;
     cout<<"compiled num1 : "<<num1<<endl;
     cout<<"compiled num2 : "<<num2<<endl;
     cout<<"compiled num3 : "<<num3<<endl;
     cout<<"compiled x : "<<x<<endl;
     cout<<"compiled count : "<<count<<endl;
     cout<<"compiled max : "<<max<<endl;
     cout<<"compiled num : "<<num<<endl;
     cout<<"compiled i : "<<i<<endl;
     cout<<"compiled j : "<<j<<endl;
     cout<<"compiled k : "<<k<<endl;


 }

 void test3_c(){
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

     cout<<"answer a : "<<a<<endl;
     cout<<"answer num1 : "<<num1<<endl;
     cout<<"answer num2 : "<<num2<<endl;
     cout<<"answer num3 : "<<num3<<endl;
     cout<<"answer x : "<<x<<endl;
     cout<<"answer count : "<<count<<endl;
     cout<<"answer max : "<<max<<endl;
     cout<<"answer num : "<<num<<endl;
     cout<<"answer i : "<<i<<endl;
     cout<<"answer j : "<<j<<endl;
     cout<<"answer k : "<<k<<endl;
}

int main(){
    test1();
    test2();
    test3_c();
    test3_s();
    return 0;
}

