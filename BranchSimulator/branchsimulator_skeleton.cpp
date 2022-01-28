#include <iostream>
#include <fstream>
#include <math.h>
#include <vector>
#include <bitset>
#include <string> 


using namespace std;

int binaryToInt(bitset<32> addr, int m) {
	int count = 0;
	int result = 0;
	for (int i = 31; i > 31 - m; i--) {
		result += addr[count] * pow(2, count);
		count++;
	}
	return result;
}

int main (int argc, char** argv) {
	ifstream config;
	config.open(argv[1]);

	int m;
	config >> m;
 
	config.close();

	ofstream out;
	string out_file_name = string(argv[2]) + ".out";
	out.open(out_file_name.c_str());
	
	ifstream trace;
	trace.open(argv[2]);

	vector<bitset<2>> ht;
	ht.resize(pow(2, m));

	bitset<2> st = bitset<2>("11"); //strong taken
	bitset<2> wt = bitset<2>("10"); //weak taken
	bitset<2> wnt = bitset<2>("01"); //weak not taken
	bitset<2> snt = bitset<2>("00"); //strong not taken

	for (int i = 0; i < pow(2, m); i++) {
		ht[i] = st;
	}
	int correctPredict = 0;
	while (!trace.eof()) {
		unsigned long pc; bool taken, myTaken;
		trace >> std::hex >> pc >> taken;
		bool prediction;
		prediction = false;
		//cout << "pc" << pc <<" taken"<< taken << endl;
		bitset<32> addr = bitset<32>(pc);
		int index = binaryToInt(addr, m);
		
		if (ht[index] == st || ht[index] == wt) { //预测taken
			myTaken = true;
			if (myTaken == taken) { //如果预测正确, ht[index]变为strong taken
				ht[index] = st;
				prediction = true;
			}
			else { //如果预测不正确
				prediction = false;
				if (ht[index] == wt) { //如果当前是weak taken 变为weak not taken
					ht[index] = wnt;
				}
				else {//如果当前是strong taken 变为weak taken
					ht[index] = wt;
				}
			}
		}
		else { //预测not take
			myTaken = false;
			if (myTaken == taken) { //如果预测正确, ht[index]变为strong not taken
				prediction = true;
				ht[index] = snt;
			}
			else { //如果预测不正确
				prediction = false;
				if (ht[index] == wnt) { //如果当前是weak not taken 变为weak taken
					ht[index] = wt;
				}
				else {//如果当前是strong not taken 变为weak not taken
					ht[index] = wnt;
				}
			}
		}
		
		if (prediction) {
			correctPredict++;
		}
		out << prediction;
	}
	cout << "m: "<<m << " correctPredictions: " << correctPredict << endl;
	trace.close();	
	out.close();
}
