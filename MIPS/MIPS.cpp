//Team member1: Jiazhao Shi, Netid: js12624, Email: js12624@nyu.edu
//Team member2: Jiahao Wang, Netid: jw7394, Email:  jw7394@nyu.edu
#include<iostream>
#include<string>
#include<vector>
#include<bitset>
#include<fstream>

using namespace std;

#define ADDU 1
#define SUBU 3
#define AND 4
#define OR  5
#define NOR 7

// Memory size.
// In reality, the memory size should be 2^32, but for this lab and space reasons,
// we keep it as this large number, but the memory is still 32-bit addressable.
#define MemSize 65536

bitset<1> isLoad;
bitset<1> isStore;
bitset<1> isIType;
bitset<3> ALUOP;
bitset<1> isJType;
bitset<1> isBranch;
bitset<1> writeEnable;
bitset<1> readMemory;
bitset<1> writeMemory;

class RF {
public:

	bitset<32> ReadData1, ReadData2;
	RF() {
		Registers.resize(32);
		Registers[0] = bitset<32>(0);
		//Registers[0] = bitset<32>("010");
		//Registers[1] = bitset<32>("101");
	}

	void ReadWrite(bitset<5> RdReg1, bitset<5> RdReg2, bitset<5> WrtReg, bitset<32> WrtData, bitset<1> WrtEnable) {
		//cout << ALUop << endl;
		unsigned long reg1 = RdReg1.to_ulong(); //rs
		unsigned long reg2 = RdReg2.to_ulong(); 
		unsigned long destReg = WrtReg.to_ulong(); 
		//cout << "i-type WrtData: " << WrtData << endl;
		if (isIType == 0 && isJType == 0) {	
			//cout <<" Registers[0]: "<< Registers[0] << endl;
			
			if (WrtEnable == 0) {
				ReadData1 = Registers[reg1];
				ReadData2 = Registers[reg2];
				//cout << "ReadData1: " << ReadData1 << endl;
				//cout << "ReadData2: " << ReadData2 << endl;
			}
			else {
				Registers[destReg] = WrtData;
				cout << "R-type Registers[destReg]: " << Registers[destReg] << endl;
			}
		}
		if (isIType == 1) {
			if (WrtEnable == 0) {				
				ReadData1 = Registers[reg1];
				if (isStore == true || isBranch == true) {
					ReadData2 = Registers[destReg];
				}
				//cout << "ReadData1: " << ReadData1 << endl;
				//cout << "ReadData2: " << ReadData2 << endl;
			}
			else {
				Registers[destReg] = WrtData;
				cout << "i-type Registers[destReg]: " << Registers[destReg] << endl;
			}
			
		}
		
		// TODO: implement!               
	}

	void OutputRF() {
		//cout << "register file out" << Registers[1]<<endl;
		ofstream rfout;
		rfout.open("RFresult.txt", std::ios_base::app);
		if (rfout.is_open()) {
			rfout << "A state of RF:" << endl;
			for (int j = 0; j < 32; j++) {
				rfout << Registers[j] << endl;
			}

		}
		else cout << "Unable to open file";
		rfout.close();

	}
private:
	vector<bitset<32> >Registers;
	
};


class ALU {
public:
	bitset<32> ALUresult;
	bitset<32> ALUOperation(bitset<3> ALUOP, bitset<32> oprand1, bitset<32> oprand2) {
		int op1 = oprand1.to_ulong();
		int op2 = oprand2.to_ulong();
		cout << op1 << endl;
		cout << op2 << endl;
		if (ALUOP == bitset<3>("001")) {
			ALUresult = bitset<32>(op1 + op2);
			//cout << oprand1 << endl;
			//cout << oprand2 << endl;
		}
		if (ALUOP == bitset<3>("011")) {
			ALUresult = bitset<32>(op1 - op2);
		}
		if (ALUOP == bitset<3>("100")) {
			ALUresult = oprand1 & oprand2;
		}
		if (ALUOP == bitset<3>("101")) {
			ALUresult = oprand1 | oprand2;
		}
		if (ALUOP == bitset<3>("111")) {
			ALUresult = ~(oprand1 | oprand2);
		}
		// TODO: implement!
		return ALUresult;
	}
};



class INSMem {
public:
	bitset<32> Instruction;
	INSMem() {
		IMem.resize(MemSize);
		ifstream imem;
		string line;
		int i = 0;
		imem.open("imem.txt");
		if (imem.is_open()) {
			while (getline(imem, line)) {
				IMem[i] = bitset<8>(line);
				i++;
			}

		}
		else cout << "Unable to open file";
		imem.close();

	}

	bitset<32> ReadMemory(bitset<32> ReadAddress) {
		unsigned long address = ReadAddress.to_ulong();
		// TODO: implement!
		// (Read the byte at the ReadAddress and the following three byte).
		for (int i = 0; i < 4; i++) {
			bitset<8> temp = IMem[address + i];
			for (int j = 0; j < 8; j++) {
				Instruction[(3 - i) * 8 + j] = temp[j];
			}

		}
		return Instruction;
	}

private:
	vector<bitset<8> > IMem;

};

class DataMem {
public:
	bitset<32> readdata;
	DataMem() {
		DMem.resize(MemSize);
		ifstream dmem;
		string line;
		int i = 0;
		dmem.open("dmem.txt");
		if (dmem.is_open()) {
			while (getline(dmem, line)) {
				DMem[i] = bitset<8>(line);
				i++;
			}
		}
		else cout << "Unable to open file";
		dmem.close();

	}
	bitset<32> MemoryAccess(bitset<32> Address, bitset<32> WriteData, bitset<1> readmem, bitset<1> writemem) {
		// TODO: implement!
		readdata = Address;
		//cout << "writemem " << writemem << endl;
		unsigned long address = Address.to_ulong();
		if (readmem == true) {
			for (int i = 0; i < 4; i++) {
				bitset<8> temp = DMem[address + i];
				//cout << "temp: "<<temp << endl;
				for (int j = 0; j < 8; j++) {
					readdata[(3 - i) * 8 + j] = temp[j];
				}

			}
			//cout << "readdata (memory): " << readdata << endl;
		}
		if (writemem == true) {
			string dataString = WriteData.to_string();
			string data1 = dataString.substr(0, 8);
			string data2 = dataString.substr(8, 16);
			string data3 = dataString.substr(16, 24);
			string data4 = dataString.substr(24, 31);
			DMem[address] = bitset<8>(data1);
			DMem[address + 1] = bitset<8>(data2);
			DMem[address + 2] = bitset<8>(data3);
			DMem[address + 3] = bitset<8>(data4);
			cout<<"DMem[address]: "<< DMem[address] << DMem[address+1] << DMem[address+2] << DMem[address + 3] << endl;
		}
		
		return readdata;
	}

	void OutputDataMem() {
		ofstream dmemout;
		dmemout.open("dmemresult.txt");
		if (dmemout.is_open()) {
			for (int j = 0; j < 1000; j++) {
				dmemout << DMem[j] << endl;
			}

		}
		else cout << "Unable to open file";
		dmemout.close();

	}

private:
	vector<bitset<8> > DMem;

};

bitset<32> signedExtension(bitset<16> immediate) {
	bitset<32> result;
	if(immediate[15] == 1)
		result = bitset<32>(4294967295);
	for (int i = 0; i < 16; i++) {
		result[i] = immediate[i];
	}
	
	return result;
}


bitset<3> decodeFunc(bitset<6> func) {
	// 3 LSBs of func field
	if (func == bitset<6>("100001")) {
		return bitset<3>("001");
	}
	if (func == bitset<6>("100011")) {
		return bitset<3>("011");
	}
	if (func == bitset<6>("100100")) {
		return bitset<3>("100");
	}
	if (func == bitset<6>("100101")) {
		return bitset<3>("101");
	}
	if (func == bitset<6>("100111")) {
		return bitset<3>("111");
	}
	cout << "cannot decode the instruction" << endl;
	return bitset<3>();
}

void decoder(bitset<32> instruction) {
	string stringIns = instruction.to_string();
	bitset<6> opcode = bitset<6>(stringIns.substr(0, 6));
	bitset<6> r_type_opcode;
	/*if (opcode == r_type_opcode) {
		return decodeFunc(bitset<6>(stringIns.substr(26, 31)));
	}*/
	if ((stringIns.substr(0,6) == "100011") || (stringIns.substr(0, 6) == "101011")) {
		ALUOP = bitset<3>("001"); // is it ld or st? // Add! Why?
	}
	else if (stringIns.substr(0, 6) == "000000") {// Is it R-Type?
		ALUOP = decodeFunc(bitset<6>(stringIns.substr(26, 31)));
		cout << "ALUOP" << ALUOP << endl;
		return;
	}
	else // everything else
		ALUOP = bitset<3>(stringIns.substr(3,6));
	cout << "ALUOP" <<ALUOP<< endl;
	if (opcode == bitset<6>("001001")) {
		isIType = true;
		return;
	}
	if (opcode == bitset<6>("000100")) {
		isBranch = true;
		writeEnable = false;
		return;
	}
	if (opcode == bitset<6>("000010")) {
		isJType = true;
		writeEnable = false;
		return;
	}
	if (opcode == bitset<6>("100011")) {
		isLoad = true;
		readMemory = true;
		isIType = true;
		cout << "lw" << endl;
		return;
			
	}
	if (opcode == bitset<6>("101011")) {
		isStore = true;
		writeEnable = false;
		writeMemory = true;
		isIType = true;
		cout << "sw" << endl;	
		return;
	}

	cout << "cannot decode the instruction" << endl;
	ALUOP = bitset<3>();

	//return bitset<3>();
	//      Func 
	//addu: 100001
	//subu: 100011
	//and: 100100
	//or: 100101
	//nor: 100111

	//   Opcode 
	//addiu: 001001
	//beq: 000100
	//j:  000010
	//lw: 100011
	//sw: 101011
}


bitset<32> nextPC(bitset<32> pc) {
	unsigned long newPC = pc.to_ulong();//bitset 转换成 unsigned long 二进制转整数
	newPC += 4;
	return bitset<32>(newPC);//long转换成bitset 整数转二进制
}


bitset<32> adder(bitset<16> immediate, bitset<32> pc) {
	bitset<32> SignExtendImm;
	for (int i = 0; i < 16; i++) {
		SignExtendImm[i + 2] = immediate[i];
	}
	cout <<"SignExtendImm: "<< SignExtendImm << endl;
	bitset<32> tempNextPC = nextPC(pc);
	cout << "tempNextPC: " << tempNextPC << endl;
	signed long result;
	result = SignExtendImm.to_ulong() + tempNextPC.to_ulong();
	return bitset<32>(result);
}

int main() {
	RF myRF;
	ALU myALU;
	INSMem myInsMem;
	DataMem myDataMem;

	bitset<32> instruction;
	bitset<32> programcounter = 0;

	while (1) {

		isLoad = false;
		isStore = false;
		isIType = false;
		ALUOP = bitset<3>();
		isJType = false;
		isBranch = false;
		writeEnable = true;
		readMemory = false;
		// Fetch

		instruction = myInsMem.ReadMemory(programcounter);
        //cout << instruction << endl;
		// If current insturciton is "11111111111111111111111111111111", then break;
		if (instruction.to_string() == "11111111111111111111111111111111") break;
		// decode(Read RF)
		decoder(instruction);
		//cout << ALUOP << endl;
		if (ALUOP == bitset<3>()) {
			programcounter = nextPC(programcounter);
			continue;
		}
		// Execute




		// Read or Write Mem
		string strIns = instruction.to_string();
		bitset<5> rs = bitset<5>(strIns.substr(6, 11));
		bitset<5> rt, rd;
		bitset<32> immediate;
		bitset<32> result;
		bitset<32> writeintoMemData;


		//read register1 and 2 from register file
		if (isIType == true) {
			rd = bitset<5>(strIns.substr(11, 16));
			immediate = signedExtension(bitset<16>(strIns.substr(16, 32))); //all I type use signed extension except ori
			//cout << "immediate signed extension: " << immediate << endl;
			cout<< "(int)immediate.to_ulong(): " << (int)immediate.to_ulong() << endl;
			myRF.ReadWrite(rs, bitset<5>(), rd, bitset<32>(), 0);
			
		}
		else if (isJType == true) {//pc(4)+address+00
			cout << "this is jump" << endl;
			bitset<32> tempAddress;
			int fourIndicator = 0;
			//programcounter[28] = 1;
			tempAddress[0] = 0;
			tempAddress[1] = 0;
			bitset<32>tempPC = nextPC(programcounter);
			for (int n = 2; n < tempAddress.size() - 2; n++) {
				if (n < 28) {
					tempAddress[n] = instruction[n - 2];
					cout << "this is ins[n]:" << n << endl;
				}
				else {
					tempAddress[n] = tempPC[n];
					cout << "this is pc[n]:" << n << endl;
				}
				cout << "this is jump address :" << tempAddress << endl;
			}
			programcounter = tempAddress;
			myRF.OutputRF();
			continue;
		}
		else {
			rt = bitset<5>(strIns.substr(11, 16));
			rd = bitset<5>(strIns.substr(16, 21));
			myRF.ReadWrite(rs, rt, rd, bitset<32>(), 0);
		}
		//cout << "immediate: " << immediate << endl;
		
		if (isBranch == true) { // only for beq
			if (myRF.ReadData1 == myRF.ReadData2) {
				programcounter = adder(bitset<16>(strIns.substr(16, 32)), programcounter);
				myRF.OutputRF();
				continue;
			}
			else {
				programcounter = nextPC(programcounter);
				cout<< "programcounter" << programcounter << endl;
				myRF.OutputRF();
				continue;
			}
		}

		//ALU operation
		if (isIType == true) {
			result = myALU.ALUOperation(ALUOP, immediate, myRF.ReadData1);
		}
		else {
			result = myALU.ALUOperation(ALUOP, myRF.ReadData1, myRF.ReadData2);
		}
		//cout << "result1: " << result << endl;
		//read and write in memory
		result = myDataMem.MemoryAccess(result, myRF.ReadData2, readMemory, writeMemory);
		//cout << "result2: " << result << endl;


		//write to register
		myRF.ReadWrite(rs, rt, rd, result, writeEnable);
		


		// Write back to RF

		myRF.OutputRF(); // dump RF;   
		programcounter = nextPC(programcounter);
	}
	myDataMem.OutputDataMem(); // dump data mem

	return 0;
}





