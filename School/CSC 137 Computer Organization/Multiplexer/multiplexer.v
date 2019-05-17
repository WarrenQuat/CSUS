`include "mux_tb.v"
`timescale 1ns/ 1ps

//new module multiplexer
//inputs a,s
//outsputs z
module multiplexer(a,s,z);
	//4 inputs required for 4x1 mux
	input [3:0] a;

	//2 select inputs
	input [1:0] s;

	//output register z
	output z;
	reg z;

	always@(*)
	begin

	    //case statement for s
	    case(s)
		2'b00: z=a[0];
		2'b01: z=a[1];
		2'b10: z=a[2];
		2'b11: z=a[3];
		//all cases must be accounted for
		default: $display("error");
	    endcase
	end
endmodule
		