`timescale 1ns / 1ps

//new full adder module
//inputs a,b,_cin
//outputs s,c_out
module full_adder(a, b, c_in,
		  s, c_out);
	input a;
	input b;
	input c_in;
	output s;
	output c_out;

	//declare wires required for structural adder
	wire[0:2] w;

	//w[0] outputs a XOR b
	assign w[0] =  a ^ b;

	//w[1] outputs w[0] AND c_in
	assign w[1] =  w[0] & c_in;

	//w[2] outputs a AND b
	assign w[2] =  a & b;

	//s output = w[0] XOR c_in
	assign s = w[0] ^ c_in;

	//c_out output = w[1] OR w[2]
	assign c_out = w[1] | w[2];
endmodule
