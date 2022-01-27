//Team member1: Jiazhao Shi, Netid: js12624, Email: js12624@nyu.edu
//Team member2: Jiahao Wang, Netid: jw7394, Email:  jw7394@nyu.edu
#include<iostream>
#include<string>
#include<vector>
#include<bitset>
#include<fstream>
using namespace std;
#define MemSize 1000 // memory size, in reality, the memory size should be 2^32, but for this lab, for the space resaon, we keep it as this large number, but the memory is still 32-bit addressable.

struct IFStruct {
    bitset<32>  PC;
    bool        nop;  
};

struct IDStruct {
    bitset<32>  Instr;
    bool        nop;  
};

struct EXStruct {
    bitset<32>  Read_data1;
    bitset<32>  Read_data2;
    bitset<16>  Imm;
    bitset<5>   Rs;
    bitset<5>   Rt;
    bitset<5>   Wrt_reg_addr;
    bool        is_I_type;
    bool        rd_mem;
    bool        wrt_mem; 
    bool        alu_op;     //1 for addu, lw, sw, 0 for subu 
    bool        wrt_enable;
    bool        nop;  
};

struct MEMStruct {
    bitset<32>  ALUresult;
    bitset<32>  Store_data;
    bitset<5>   Rs;
    bitset<5>   Rt;    
    bitset<5>   Wrt_reg_addr;
    bool        rd_mem;
    bool        wrt_mem; 
    bool        wrt_enable;    
    bool        nop;    
};

struct WBStruct {
    bitset<32>  Wrt_data;
    bitset<5>   Rs;
    bitset<5>   Rt;     
    bitset<5>   Wrt_reg_addr;
    bool        wrt_enable;
    bool        nop;     
};

struct stateStruct {
    IFStruct    IF;
    IDStruct    ID;
    EXStruct    EX;
    MEMStruct   MEM;
    WBStruct    WB;
};

class RF
{
    public: 
        bitset<32> Reg_data;
     	RF()
    	{ 
			Registers.resize(32);  
			Registers[0] = bitset<32> (0); 
            /*Registers[1] = bitset<32>(2);
            Registers[2] = bitset<32>(1);*/
        }
	
        bitset<32> readRF(bitset<5> Reg_addr)
        {   
            Reg_data = Registers[Reg_addr.to_ulong()];
            return Reg_data;
        }
    
        void writeRF(bitset<5> Reg_addr, bitset<32> Wrt_reg_data)
        {
            Registers[Reg_addr.to_ulong()] = Wrt_reg_data;
        }
		 
		void outputRF()
		{
			ofstream rfout;
			rfout.open("RFresult.txt",std::ios_base::app);
			if (rfout.is_open())
			{
				rfout<<"State of RF:\t"<<endl;
				for (int j = 0; j<32; j++)
				{        
					rfout << Registers[j]<<endl;
				}
			}
			else cout<<"Unable to open file";
			rfout.close();               
		} 
			
	private:
		vector<bitset<32> >Registers;	
};

class INSMem
{
	public:
        bitset<32> Instruction;
        INSMem()
        {       
			IMem.resize(MemSize); 
            ifstream imem;
			string line;
			int i=0;
			imem.open("imem.txt");
			if (imem.is_open())
			{
				while (getline(imem,line))
				{      
					IMem[i] = bitset<8>(line);
					i++;
				}                    
			}
            else cout<<"Unable to open file";
			imem.close();                     
		}
                  
		bitset<32> readInstr(bitset<32> ReadAddress) 
		{    
			string insmem;
			insmem.append(IMem[ReadAddress.to_ulong()].to_string());
			insmem.append(IMem[ReadAddress.to_ulong()+1].to_string());
			insmem.append(IMem[ReadAddress.to_ulong()+2].to_string());
			insmem.append(IMem[ReadAddress.to_ulong()+3].to_string());
			Instruction = bitset<32>(insmem);		//read instruction memory
			return Instruction;     
		}     
      
    private:
        vector<bitset<8> > IMem;     
};
      
class DataMem    
{
    public:
        bitset<32> ReadData;  
        DataMem()
        {
            DMem.resize(MemSize); 
            ifstream dmem;
            string line;
            int i=0;
            dmem.open("dmem.txt");
            if (dmem.is_open())
            {
                while (getline(dmem,line))
                {      
                    DMem[i] = bitset<8>(line);
                    i++;
                }
            }
            else cout<<"Unable to open file";
                dmem.close();          
        }
		
        bitset<32> readDataMem(bitset<32> Address)
        {	
			string datamem;
            datamem.append(DMem[Address.to_ulong()].to_string());
            datamem.append(DMem[Address.to_ulong()+1].to_string());
            datamem.append(DMem[Address.to_ulong()+2].to_string());
            datamem.append(DMem[Address.to_ulong()+3].to_string());
            ReadData = bitset<32>(datamem);		//read data memory
            return ReadData;               
		}
            
        void writeDataMem(bitset<32> Address, bitset<32> WriteData)            
        {
            DMem[Address.to_ulong()] = bitset<8>(WriteData.to_string().substr(0,8));
            DMem[Address.to_ulong()+1] = bitset<8>(WriteData.to_string().substr(8,8));
            DMem[Address.to_ulong()+2] = bitset<8>(WriteData.to_string().substr(16,8));
            DMem[Address.to_ulong()+3] = bitset<8>(WriteData.to_string().substr(24,8));  
        }   
                     
        void outputDataMem()
        {
            ofstream dmemout;
            dmemout.open("dmemresult.txt");
            if (dmemout.is_open())
            {
                for (int j = 0; j< 1000; j++)
                {     
                    dmemout << DMem[j]<<endl;
                }
                     
            }
            else cout<<"Unable to open file";
            dmemout.close();               
        }             
      
    private:
		vector<bitset<8> > DMem;      
};  

void printState(stateStruct state, int cycle)
{
    ofstream printstate;
    printstate.open("stateresult.txt", std::ios_base::app);
    if (printstate.is_open())
    {
        printstate<<"State after executing cycle:\t"<<cycle<<endl; 
        
        printstate<<"IF.PC:\t"<<state.IF.PC.to_ulong()<<endl;        
        printstate<<"IF.nop:\t"<<state.IF.nop<<endl; 
        
        printstate<<"ID.Instr:\t"<<state.ID.Instr<<endl; 
        printstate<<"ID.nop:\t"<<state.ID.nop<<endl;
        
        printstate<<"EX.Read_data1:\t"<<state.EX.Read_data1<<endl;
        printstate<<"EX.Read_data2:\t"<<state.EX.Read_data2<<endl;
        printstate<<"EX.Imm:\t"<<state.EX.Imm<<endl; 
        printstate<<"EX.Rs:\t"<<state.EX.Rs<<endl;
        printstate<<"EX.Rt:\t"<<state.EX.Rt<<endl;
        printstate<<"EX.Wrt_reg_addr:\t"<<state.EX.Wrt_reg_addr<<endl;
        printstate<<"EX.is_I_type:\t"<<state.EX.is_I_type<<endl; 
        printstate<<"EX.rd_mem:\t"<<state.EX.rd_mem<<endl;
        printstate<<"EX.wrt_mem:\t"<<state.EX.wrt_mem<<endl;        
        printstate<<"EX.alu_op:\t"<<state.EX.alu_op<<endl;
        printstate<<"EX.wrt_enable:\t"<<state.EX.wrt_enable<<endl;
        printstate<<"EX.nop:\t"<<state.EX.nop<<endl;        

        printstate<<"MEM.ALUresult:\t"<<state.MEM.ALUresult<<endl;
        printstate<<"MEM.Store_data:\t"<<state.MEM.Store_data<<endl; 
        printstate<<"MEM.Rs:\t"<<state.MEM.Rs<<endl;
        printstate<<"MEM.Rt:\t"<<state.MEM.Rt<<endl;   
        printstate<<"MEM.Wrt_reg_addr:\t"<<state.MEM.Wrt_reg_addr<<endl;              
        printstate<<"MEM.rd_mem:\t"<<state.MEM.rd_mem<<endl;
        printstate<<"MEM.wrt_mem:\t"<<state.MEM.wrt_mem<<endl; 
        printstate<<"MEM.wrt_enable:\t"<<state.MEM.wrt_enable<<endl;         
        printstate<<"MEM.nop:\t"<<state.MEM.nop<<endl;        

        printstate<<"WB.Wrt_data:\t"<<state.WB.Wrt_data<<endl;
        printstate<<"WB.Rs:\t"<<state.WB.Rs<<endl;
        printstate<<"WB.Rt:\t"<<state.WB.Rt<<endl;        
        printstate<<"WB.Wrt_reg_addr:\t"<<state.WB.Wrt_reg_addr<<endl;
        printstate<<"WB.wrt_enable:\t"<<state.WB.wrt_enable<<endl;        
        printstate<<"WB.nop:\t"<<state.WB.nop<<endl; 
    }
    else cout<<"Unable to open file";
    printstate.close();
}
 
bitset<32> signedExtension(bitset<16> immediate) {
    bitset<32> result;
    if (immediate[15] == 1)
        result = bitset<32>(4294967295);
    for (int i = 0; i < 16; i++) {
        result[i] = immediate[i];
    }

    return result;
}

int main()
{
    RF myRF;
    INSMem myInsMem;
    DataMem myDataMem;
    IFStruct IF = {
        0, //PC 
        0, //nop
    };

    IDStruct ID = {
        0, //Instr
        1, //nop
    };

    EXStruct EX = {
        0, //Read_data1
        0, //Read_data2
        0, //Imm
        0, //Rs
        0, //Rt
        0, //Wrt_reg_addr
        0, //is_I_type
        0, //rd_mem
        0, //wrt_mem
        0, //alu_op
        0, //wrt_enable
        1, //nop

        
    };

    MEMStruct MEM = {
        0, //ALUresult
        0, //Store_data
        0, //Rs
        0, //Rt
        0, //Wrt_reg_addr
        0, //rd_mem
        0, //wrt_mem
        0, //wrt_enable 
        1, //nop

        
    };
    WBStruct WB = {
        0, //Wrt_data
        0, //Rs
        0, //Rt
        0, //Wrt_reg_addr
        0, //wrt_enable
        1, //nop
        
    };

    stateStruct state = {
        IF,
        ID,
        EX ,
        MEM,
        WB,
    };
    stateStruct newState = state;
    int cycle  = 0;         
    while (1) {

        /* --------------------- WB stage --------------------- */
        //WB(write back)返回到寄存器
        if (state.WB.nop == 0) {
            if (state.WB.wrt_enable) {
                myRF.writeRF(state.WB.Wrt_reg_addr, state.WB.Wrt_data);
            }
        }

        /* --------------------- MEM stage --------------------- */
        //Mem(memory)进入内存
        if (state.MEM.nop == 0) {
            newState.WB.Rs = state.MEM.Rs;
            newState.WB.Rt = state.MEM.Rt;
            newState.WB.wrt_enable = state.MEM.wrt_enable;
            newState.WB.Wrt_reg_addr = state.MEM.Wrt_reg_addr;
            newState.WB.Wrt_data = state.MEM.ALUresult;
            if (newState.MEM.rd_mem) {
                newState.WB.Wrt_data = myDataMem.readDataMem(state.MEM.ALUresult);
            }
            if (newState.MEM.wrt_mem) {
                myDataMem.writeDataMem(state.MEM.ALUresult,state.MEM.Store_data);
                cout << "address " << state.MEM.ALUresult << " store data: " << state.MEM.Store_data << endl;
            }
        }
        newState.WB.nop = state.MEM.nop;

        /* --------------------- EX stage --------------------- */
        //EX(execution)进行运算（ALUOP）
        if (state.EX.nop == 0) {
            newState.MEM.Rs = state.EX.Rs;
            newState.MEM.Rt = state.EX.Rt;
            newState.MEM.wrt_enable = state.EX.wrt_enable;
            newState.MEM.Wrt_reg_addr = state.EX.Wrt_reg_addr;
            newState.MEM.rd_mem = state.EX.rd_mem;
            newState.MEM.wrt_mem = state.EX.wrt_mem;
            
            //MEM-EX hazards
            if (!state.WB.nop && state.WB.wrt_enable) {
                if (state.WB.Wrt_reg_addr == state.EX.Rs) {
                    state.EX.Read_data1 = state.WB.Wrt_data;
                } 
                if (state.WB.Wrt_reg_addr == state.EX.Rt) {
                    if (!state.EX.rd_mem) {
                        state.EX.Read_data2 = state.WB.Wrt_data;
                    }
                }
            }

            //EX-EX hazards 
            if ((!state.MEM.nop) && (!state.MEM.rd_mem) &&
                (!state.MEM.wrt_mem) && (state.MEM.wrt_enable)) {
                if (state.MEM.Wrt_reg_addr == state.EX.Rs) {
                    state.EX.Read_data1 = state.MEM.ALUresult;
                } else if (state.MEM.Wrt_reg_addr == state.EX.Rt) {
                    if ((state.EX.wrt_mem) || ((!state.EX.is_I_type) && (state.EX.wrt_enable))) {
                        state.EX.Read_data2 = state.MEM.ALUresult;
                    }
                }
            }
            

            //R-type
            if (!state.EX.is_I_type) {
                int op1 = state.EX.Read_data1.to_ulong();
                int op2 = state.EX.Read_data2.to_ulong();
               // cout << "EX.Read_data1: " << EX.Read_data1 << endl;
                //cout << "EX.Read_data2: " << EX.Read_data2 << endl;
                //addu
                if (state.EX.alu_op == 1) {
                    //cout << "addu EX" << endl;
                    newState.MEM.ALUresult = bitset<32>(op1 + op2);
                } else { //subu
                    newState.MEM.ALUresult = bitset<32>(op1 - op2);
                }
            } else { //I-type
                int op1 = state.EX.Read_data1.to_ulong();
                int op2 = signedExtension(state.EX.Imm).to_ulong();
                newState.MEM.ALUresult = bitset<32>(op1 + op2);
                newState.MEM.Store_data = state.EX.Read_data2;
                //cout << "lw EX" << endl;
                
            }

        }
        newState.MEM.nop = state.EX.nop;

        /* --------------------- ID stage --------------------- */
        //ID(instruction decode)decode 指令以及进入register寻找对应
        if (state.ID.nop == 0) {
             string instr = state.ID.Instr.to_string();
             string opcode = instr.substr(0, 6);
             bitset<5> rs = bitset<5>(instr.substr(6, 5));
             bitset<5> rt = bitset<5>(instr.substr(11, 5));
             bitset<5> rd = bitset<5>(instr.substr(16, 5));
             string funcCode = instr.substr(26, 6);
             bitset<16> imm = bitset<16>(instr.substr(16, 16));
             newState.EX.Rs = rs;
             newState.EX.Rt = rt;
             //R-type instruction
             if (opcode == "000000") {
                 newState.EX.Read_data1 = myRF.readRF(rs);
                 newState.EX.Read_data2 = myRF.readRF(rt);
                 newState.EX.Wrt_reg_addr = rd;
                 newState.EX.rd_mem = 0;
                 newState.EX.wrt_mem = 0;
                 newState.EX.is_I_type = 0;
                 newState.EX.wrt_enable = 1;
                 //addu
                 if (funcCode == "100001") {
                     newState.EX.alu_op = 1;
                 } else { //subu
                     newState.EX.alu_op = 0;
                 }
             } else { //I-type Instruction
                 newState.EX.alu_op = 1;
                 newState.EX.is_I_type = 1;
                 newState.EX.Imm = imm;
                 newState.EX.wrt_enable = 0;
                 newState.EX.Read_data1 = myRF.readRF(rs);
                 if (opcode == "100011") { //lw
                     newState.EX.Wrt_reg_addr = rt;
                     newState.EX.rd_mem = 1;
                     newState.EX.wrt_enable = 1;
                 } else if (opcode == "101011") {//sw
                     newState.EX.Read_data2 = myRF.readRF(rt);
                     newState.EX.wrt_mem = 1;
                     newState.EX.wrt_enable = 0;
                 } else { //beq
                     newState.EX.wrt_enable = 0;
                     newState.EX.rd_mem = 0;
                     newState.EX.wrt_mem = 0;
                     if (myRF.readRF(rs) != myRF.readRF(rt)) {
                         newState.EX.nop = newState.IF.nop = 0;
                         newState.ID.nop = 1;
                         unsigned long extend_imm = bitset<30>(signedExtension(imm).to_string().substr(2, 30)).to_ulong() * 4;
                         newState.IF.PC = state.IF.PC.to_ulong() + extend_imm;
                         printState(newState, cycle);
                         state = newState;
                         cycle++;
                         continue;
                     }    
                 }
             }
             if ((!state.EX.nop) && (state.EX.rd_mem)) {
                 if ((state.EX.Wrt_reg_addr == rs) || ((state.EX.Wrt_reg_addr ==
                     rt) && (0 == newState.EX.is_I_type))) {
                     newState.EX.nop = 1;
                     newState.ID = state.ID;
                     newState.IF = state.IF;
                     printState(newState, cycle);
                     state = newState;
                     cycle++;
                     continue;
                 }
             }
        }
        newState.EX.nop = state.ID.nop;

        //addu: 100001
        //subu: 100011
        //beq: 000100
        //lw: 100011
        //sw: 101011
        /* --------------------- IF stage --------------------- */
        //IF(instruction fetch)获取指令
        if (state.IF.nop == 0) {
            newState.ID.Instr = myInsMem.readInstr(state.IF.PC);
            cout <<"current instr: " << newState.ID.Instr << endl;
            if (newState.ID.Instr == 0xffffffff) {
                state.IF.nop = newState.IF.nop = 1;
            } else {
                newState.IF.PC = state.IF.PC.to_ulong() + 4;
            }
        }
        newState.ID.nop = state.IF.nop;

             
        if (state.IF.nop && state.ID.nop && state.EX.nop && state.MEM.nop && state.WB.nop)
            break;
        
        printState(newState, cycle); //print states after executing cycle 0, cycle 1, cycle 2 ... 

        state = newState; /*The end of the cycle and updates the current state with the values calculated in this cycle */ 

        cycle++;
    }
    
    myRF.outputRF(); // dump RF;	
	myDataMem.outputDataMem(); // dump data mem 
	
	return 0;
}