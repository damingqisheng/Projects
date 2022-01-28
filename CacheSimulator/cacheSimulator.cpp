/*
   Cache Simulator
   Level one L1 and level two L2 cache parameters are read from file
   (block size, line per set and set per cache).
   The 32 bit address is divided into:
   -tag bits (t)
   -set index bits (s)
   -block offset bits (b)

   s = log2(#sets)   b = log2(block size)  t=32-s-b
*/

#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include <iomanip>
#include <stdlib.h>
#include <cmath>
#include <bitset>


using namespace std;
//access state:
#define NA 0 // no action
#define RH 1 // read hit
#define RM 2 // read miss
#define WH 3 // Write hit
#define WM 4 // write miss


struct config{
  int L1blocksize;
  int L1setsize;
  int L1size;
  int L2blocksize;
  int L2setsize;
  int L2size;
};

struct cacheBlock { //validbit, tag, dirtybit, blockSize
    unsigned long tag = 0;
    bool isValid = false;
    bool isDirty = false;
    vector<bitset<8>> blockContents;
};
//you can define the cache class here, or design your own data structure for L1 and L2 cache
   class cache {
       
       public:
           int tagbit1, indexBit1, offsetbit1, tagbit2, indexBit2, offsetbit2;
           vector<vector<cacheBlock>> cacheTable1;
           vector<vector<cacheBlock>> cacheTable2;
           bool l1IsFully = false;
           bool l2IsFully = false;
           cache(config cacheconfig) {
               int rowSize1 = cacheconfig.L1size;
               int rowSize2 = cacheconfig.L2size;
               int colSize1 = cacheconfig.L1setsize;
               int colSize2 = cacheconfig.L2setsize;
               int blockSize1 = cacheconfig.L1blocksize;
               int blockSize2 = cacheconfig.L2blocksize;
               if (colSize1 == 0) {
                   colSize1 = 1;
                   l1IsFully = true;
               }
               if (colSize2 == 0) {
                   colSize2 = 1;
                   l2IsFully = true;
               }
               indexBit1 = (int)log2((rowSize1) * (pow(2, 10)) / (blockSize1 * colSize1));
               indexBit2 = (int)log2((rowSize2) * (pow(2, 10)) / (blockSize2 * colSize2));
               offsetbit1 = (int)log2(blockSize1);
               offsetbit2 = (int)log2(blockSize2);
               tagbit1 = 32 - indexBit1 - offsetbit1;
               tagbit2 = 32 - indexBit2 - offsetbit2;
               cacheTable1.resize((rowSize1 * 1024) / (blockSize1 * colSize1));
               cacheTable2.resize((rowSize2 * 1024) / (blockSize2 * colSize2));
               for (int i = 0; i < (rowSize1 * 1024) / (blockSize1 * colSize1); i++) {
                   cacheTable1[i].resize(colSize1);
                   for (int j = 0; j < colSize1; j++) {
                       cacheTable1[i][j].blockContents.resize(blockSize1);
                   }
               }
               //cout << "(rowSize2 * 1024) / (blockSize2 * colSize2)" << (rowSize2 * 1024) / (blockSize2 * colSize2) << endl;
               int i = 0;
               //cout << "cacheTable2.size()" << cacheTable2.size() << endl;
               for (int i = 0; i < cacheTable2.size(); i++) {

                   //cout << "cacheTable2.size()" << cacheTable2.size() << endl;
                   cacheTable2[i].resize(colSize2);
                   for (int j = 0; j < colSize2; j++) {
                     cacheTable2[i][j].blockContents.resize(blockSize2);
                   }
               } 
               if (l1IsFully) {
                   indexBit1 = 0;
                   tagbit1 = 32 - offsetbit1;
               }
               if (l2IsFully) {
                   indexBit2 = 0;
                   tagbit2 = 32 - offsetbit2;
               }
           }
   };


int main(int argc, char* argv[]){

  

  config cacheconfig;
  ifstream cache_params;
  string dummyLine;
  cache_params.open(argv[1]);
  while(!cache_params.eof())  // read config file
  {
    cache_params>>dummyLine;
    cache_params>>cacheconfig.L1blocksize;
    cache_params>>cacheconfig.L1setsize;              
    cache_params>>cacheconfig.L1size;
    cache_params>>dummyLine;              
    cache_params>>cacheconfig.L2blocksize;           
    cache_params>>cacheconfig.L2setsize;        
    cache_params>>cacheconfig.L2size;
  }

  
  
  // Implement by you: 
  // initialize the hirearch cache system with those configs
  // probably you may define a Cache class for L1 and L2, or any data structure you like
  cache caches(cacheconfig);
  vector<vector<cacheBlock>> cacheTable1 = caches.cacheTable1;
  vector<vector<cacheBlock>> cacheTable2 = caches.cacheTable2;
  vector<int> blocksToReplace1;
  vector<int> blocksToReplace2;
  blocksToReplace1.resize(caches.cacheTable1.size());
  blocksToReplace2.resize(caches.cacheTable2.size());
  int blockToReplace1 = 0;
  int blockToReplace2 = 0;
  int L1AcceState =0; // L1 access state variable, can be one of NA, RH, RM, WH, WM;
  int L2AcceState =0; // L2 access state variable, can be one of NA, RH, RM, WH, WM;
  

  ifstream traces;
  ofstream tracesout;
  string outname;
  outname = string(argv[2]) + ".out";

  traces.open(argv[2]);
  tracesout.open(outname.c_str());

  string line;
  string accesstype;  // the Read/Write access type from the memory trace;
  string xaddr;       // the address from the memory trace store in hex;
  unsigned int addr;  // the address from the memory trace store in unsigned int;        
  bitset<32> accessaddr; // the address from the memory trace store in the bitset;
  int totalcount = 1; //for debug;
  if (traces.is_open()&&tracesout.is_open()){    
    while (getline (traces,line)){   // read mem access file and access Cache
        if (totalcount % 1000 == 0) {
            cout << totalcount << endl;
        }
        
      L1AcceState = 0;
      L2AcceState = 0;
      istringstream iss(line); 
      if (!(iss >> accesstype >> xaddr)) {break;}
      stringstream saddr(xaddr);
      saddr >> std::hex >> addr;
      accessaddr = bitset<32> (addr);
      string strAddr = accessaddr.to_string();
      unsigned long tag1 = bitset<32>(strAddr.substr(0, caches.tagbit1)).to_ulong();
      unsigned long index1 = bitset<32>(strAddr.substr(caches.tagbit1, caches.indexBit1)).to_ulong();
      unsigned long offset1 = bitset<32>(strAddr.substr(32 - caches.offsetbit1)).to_ulong();

      unsigned long tag2 = bitset<32>(strAddr.substr(0, caches.tagbit2)).to_ulong();
      unsigned long index2 = bitset<32>(strAddr.substr(caches.tagbit2, caches.indexBit2)).to_ulong();
      unsigned long offset2 = bitset<32>(strAddr.substr(32 - caches.offsetbit2)).to_ulong();
      // access the L1 and L2 Cache according to the trace;
      //cout << "line183" << endl;
      if (accesstype.compare("R")==0)
      {
          
        //Implement by you:
        // read access to the L1 Cache, 
        //  and then L2 (if required), 
        //  update the L1 and L2 access state variable;
          
          vector<cacheBlock> tempblocks = cacheTable1[index1];
          cacheBlock tempblock;
          bool found = false;
          //检查是否在l1中存在想要找的block
          if (!caches.l1IsFully) {
              for (cacheBlock block : tempblocks) {
                  if (block.isValid && block.tag == tag1) {
                      found = true;
                      L1AcceState = 1;
                  }
              }
              if (!found) {
                  tempblock = cacheTable1[index1][blocksToReplace1[index1]];
                  cacheTable1[index1][blocksToReplace1[index1]].isDirty = 0;
                  cacheTable1[index1][blocksToReplace1[index1]].isValid = 1;
                  cacheTable1[index1][blocksToReplace1[index1]].tag = tag1;
                  blocksToReplace1[index1]++;
                  if (blocksToReplace1[index1] >= cacheconfig.L1setsize) {
                      blocksToReplace1[index1] = 0;
                  }
              }
              
              
          } else {
              for (int i = 0; i < cacheTable1.size(); i++) {
                  cacheBlock block = cacheTable1[i][0];
                  if (block.isValid && block.tag == tag1) {
                      found = true;
                      L1AcceState = 1;
                  }
              }
              if (!found) {
                  tempblock = cacheTable1[blockToReplace1][0];
                  cacheTable1[blockToReplace1][0].isDirty = 0;
                  cacheTable1[blockToReplace1][0].isValid = 1;
                  cacheTable1[blockToReplace1][0].tag = tag1;
                  blockToReplace1++;
              }
          } 
          if (!found) { //l1没找到在l2中找
              L1AcceState = 2;
              tempblocks = cacheTable2[index2];
              int tempcount = 0; //for debug;
              /*if (index2 == 77) {
                  cout << "R totalCount " << totalcount << endl;
                  cout << "blocksToReplace2[index2]: " << blocksToReplace2[index2] << endl;
              }*/
              
              if (!caches.l2IsFully) {
                  for (cacheBlock block : tempblocks) {
                      /*
                        if (index2 == 77) {
                          cout<< "block.tag: "<< tempcount<<" is: "<< block.tag << endl;
                      }
                      */ 
                      if (block.isValid && block.tag == tag2) {
                          found = true;
                          L2AcceState = 1;

                      }
                      tempcount++;
                  }
                  if (!found) {
                      cacheTable2[index2][blocksToReplace2[index2]].isDirty = tempblock.isDirty;
                      cacheTable2[index2][blocksToReplace2[index2]].isValid = tempblock.isValid;
                      cacheTable2[index2][blocksToReplace2[index2]].tag = tag2;
                      blocksToReplace2[index2]++;
                      if (blocksToReplace2[index2] >= cacheconfig.L2setsize) {
                          blocksToReplace2[index2] = 0;
                      }
                  }
                  
              } else {
                  for (int i = 0; i < cacheTable2.size(); i++) {
                      cacheBlock block = cacheTable2[i][0];
                      if (block.isValid && block.tag == tag2) {
                          found = true;
                          L2AcceState = 1;
                      }
                  }
                  if (!found) {
                      cacheTable2[blockToReplace2][0].isDirty = tempblock.isDirty;
                      cacheTable2[blockToReplace2][0].isValid = tempblock.isValid;
                      cacheTable2[blockToReplace2][0].tag = tag2;
                      blockToReplace2++;
                  }
                 
                  /*cout << "totalcount:" << totalcount << endl;
                  cout << "blockToReplace1:" << blockToReplace1 << endl;
                  cout << "tag1:" << tag1 << endl;
                  cout << "cacheTable1[blockToReplace1][0].tag:" << cacheTable1[blockToReplace1][0].tag << endl;*/
                  
                  
              }
              if (!found) {
                  L2AcceState = 2;
              }
              //从l1中删除block存入l2中
              if (tempblock.isDirty) { //如果dirtybit是1，执行write back。如果dirty bit 是0什么都不做
                  bitset<32> tempblockTag = bitset<32>(tempblock.tag);
                  bitset<32> tempblockIndex = bitset<32>(index1);
                  string tempblockTagStr = tempblockTag.to_string().substr(32 - caches.tagbit1);
                  string tempblockIndexStr = tempblockIndex.to_string().substr(32 - caches.indexBit1);
                  string tag1AndIndex1 = tempblockTagStr + tempblockIndexStr;


                  unsigned long newTag2 = bitset<32>(tag1AndIndex1.substr(0, caches.tagbit2)).to_ulong();
                  unsigned long newIndex2 = bitset<32>(tag1AndIndex1.substr(tag1AndIndex1.size() - caches.indexBit2)).to_ulong();
                  vector<cacheBlock> newTempBlock = cacheTable2[newIndex2];
                  // 在block2中找到l1中移除的block并修改
                  if (!caches.l2IsFully) {
                      for (int i = 0; i < newTempBlock.size(); i++) {
                          cacheBlock block = newTempBlock[i];
                          if (block.isValid && block.tag == newTag2) {
                              
                              cacheTable2[newIndex2][i].isDirty = tempblock.isDirty;
                              cacheTable2[newIndex2][i].isValid = tempblock.isValid;
                              cacheTable2[newIndex2][i].tag = newTag2;
                          }
                      }
                  }
                  else {
                      for (int i = 0; i < cacheTable2.size(); i++) {
                          cacheBlock block = cacheTable2[i][0];
                          if (block.isValid && block.tag == tag2) {
                              cacheTable2[i][0].isDirty = tempblock.isDirty;
                              cacheTable2[i][0].isValid = tempblock.isValid;
                              cacheTable2[i][0].tag = newTag2;
                          }
                      }
                  }
              }
              
              
              
              
          }

      }
      else 
      {    
     // cout << "line333" << endl;
        //Implement by you:
        // write access to the L1 Cache, 
        //and then L2 (if required), 
        //update the L1 and L2 access state variable;
          vector<cacheBlock> tempblocks = cacheTable1[index1];
          bool found = false;
          int count = 0;
          
          if (!caches.l1IsFully) {
              for (cacheBlock block : tempblocks) {
                  
                  if (block.isValid && block.tag == tag1) {
                      found = true;
                      L1AcceState = 3;
                      cacheTable1[index1][count].isDirty = 1;
                  }
                  count++;
              }
          } else {
              for (int i = 0; i < cacheTable1.size(); i++) {
                  cacheBlock block = cacheTable1[i][0];
                  if (block.isValid && block.tag == tag1) {
                      found = true;
                      L1AcceState = 3;
                      cacheTable1[i][0].isDirty = 1;
                  }
              }
              
          }
          if (!found) {
              L1AcceState = 4;
              tempblocks = cacheTable2[index2];
              cacheBlock tempblock;
              /*if (index2 == 77) {
                  cout << "W totalCount " << totalcount << endl;
                  cout << "blocksToReplace2[index2]: " << blocksToReplace2[index2] << endl;
              }*/
              if (!caches.l2IsFully) {
                  for (cacheBlock block : tempblocks) {
                      /*if (index2 == 77) {
                          cout << "block.tag: " << count << " is: " << block.tag << endl;
                      }*/
                      
                      if (block.isValid && block.tag == tag2) {
                          found = true;
                          L2AcceState = 3;

                      }
                  }
                  
              } else {
                
                  for (int i = 0; i < cacheTable2.size(); i++) {
                      cacheBlock block = cacheTable2[i][0];
                      if (block.isValid && block.tag == tag2) {
                          found = true;
                          L2AcceState = 3;
                      }
                  }
                
                  
                 
              }
              if (!found) {
                  L2AcceState = 4;
              }

              /*//从l1中删除block存入l2中
              bitset<32> tempblockTag = bitset<32>(tempblock.tag);
              bitset<32> tempblockIndex = bitset<32>(index1);
              string tempblockTagStr = tempblockTag.to_string().substr(32 - caches.tagbit1);
              string tempblockIndexStr = tempblockIndex.to_string().substr(32 - caches.indexBit1);
              string tag1AndIndex1 = tempblockTagStr + tempblockIndexStr;
              unsigned long newTag2 = bitset<32>(tag1AndIndex1.substr(0, caches.tagbit2)).to_ulong();
              unsigned long newIndex2 = bitset<32>(tag1AndIndex1.substr(tag1AndIndex1.size() - caches.indexBit2)).to_ulong();
              vector<cacheBlock> newTempBlock = cacheTable2[newIndex2];
              bool found2 = false;
              //检查是否在l2中已经存在该block
              if (!caches.l2IsFully) {
                  for (int i = 0; i < newTempBlock.size(); i++) {
                      cacheBlock block = newTempBlock[i];
                      if (block.isValid && block.tag == newTag2) {
                          found2 = true;
                          cacheTable2[newIndex2][i].isDirty = tempblock.isDirty;
                          cacheTable2[newIndex2][i].isValid = tempblock.isValid;
                          cacheTable2[newIndex2][i].tag = tempblock.tag;

                      }
                  }
              }
              else {
                  for (int i = 0; i < cacheTable2.size(); i++) {
                      cacheBlock block = cacheTable1[i][0];
                      if (block.isValid && block.tag == tag2) {
                          found2 = true;
                          cacheTable2[i][0].isDirty = tempblock.isDirty;
                          cacheTable2[i][0].isValid = tempblock.isValid;
                          cacheTable2[i][0].tag = tempblock.tag;
                      }
                  }
              }
              //如果不存在按指针删除一个block，用新的代替
              if (!found2) {
                  if (caches.l2IsFully) {
                      cacheTable2[blockToReplace2][0].isDirty = tempblock.isDirty;
                      cacheTable2[blockToReplace2][0].isValid = tempblock.isValid;
                      cacheTable2[blockToReplace2][0].tag = tempblock.tag;
                  }
              }
              else {
                  cacheTable2[index1][blockToReplace2].isDirty = tempblock.isDirty;
                  cacheTable2[index1][blockToReplace2].isValid = tempblock.isValid;
                  cacheTable2[index1][blockToReplace2].tag = tempblock.tag;
              }
              blockToReplace2++;
              */
          }
      }
      //cout << "L1AcceState: " << L1AcceState << endl;
      //cout << "L2AcceState: " << L2AcceState << endl;
      
      if (blockToReplace1 >= caches.cacheTable1.size()) {
          blockToReplace1 = 0;
      }
      
      
      if (blockToReplace2 >= caches.cacheTable2.size()) {
          blockToReplace2 = 0;
      }
      
      


  

      // Output hit/miss results for L1 and L2 to the output file
      tracesout<< L1AcceState << " " << L2AcceState << endl;

      totalcount++;
    }
    traces.close();
    tracesout.close(); 
  }
  else cout<< "Unable to open trace or traceout file ";

  return 0;
}
