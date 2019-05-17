//Warren Quattrocchi
//CSC 137 Moore FSM '1010' sequencer 

`timescale 1ns / 1ps
module moore_fsm(input x, clk, rst,
            output reg z);
    parameter s0 = 3'b000, s1 = 3'b001, s2 = 3'b010, s3 = 3'b011, s4 = 3'b100; 
    reg[2:0] ns, cs;
    
    //Sequential logic
    always @(posedge clk or negedge rst)
        if(!rst)
            cs <= s0;
        else
            cs <= ns;
    
    //Combinational next state logic
    always @(*)
        case(cs)
	
	    //Current state == 000
            s0: begin
                if(x == 1) 
                    ns = s1;
                else
                    ns = s0;
                end
               
   	    //current state == 001 
            s1: begin
                if(x == 1)
                    ns = s1;
                else 
                    ns = s2;
                end
            
 	    //Current state = 010    
            s2: begin
                if(x == 1)
                    ns = s3;
                else
                    ns = s0;
                end
 
	    //Current state = 011               
            s3: begin
                if(x == 1)
                    ns = s1;
                else
                    ns = s4;
                end

 	    //Current state = 100                 
            s4: begin
                if(x == 1)
                    ns = s3;
                else
                    ns = s0;
                end
        endcase

    //Combinational output logic
    always@(*)
        case(cs)
            s0: z = 0;
            s1: z = 0;
            s2: z = 0;
            s3: z = 0;
            s4: z = 1;
        endcase
endmodule

module moore_tb();
    
    //test input
    reg[29:0] inputTest = 30'b001101101000010010011010101010;
    reg x;
    reg clk;
    reg rst;
    wire z;
    integer i;
    
    //create new instance of moore_fsm
    moore_fsm uut(.x(x), .clk(clk), .rst(rst), .z(z));
    
    initial begin
	$display("Warren Quattrocchi, CSC 137, Moore 1010 Sequence Detector");
        clk = 0;
        rst = 0;
        #30;
        
        rst = 1;

	//loop through input
        for(i = 29; i>= 0; i= i - 1)
        begin
            x = inputTest[i];
	    //switch clk on and off
            #5 clk = 1;
            #5 clk = 0;
            $display("time = %3d, x = %b, z = %b", $time, x, z);
        end
        #400 $finish;
  
    end
endmodule
