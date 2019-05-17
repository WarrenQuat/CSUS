`include "full_adder.v"
`include "carry_tb.v"
`timescale 1ns / 1ps

//create new module
//inputs a,b,c_in
//outputs s, c_out
module carry_adder(a, b, c_in,
		   s, c_out);
	input [7:0] a;
	input [7:0] b;
	input c_in;
	output [7:0] s;
	output c_out;

	//create 7 wires
	wire[6:0] w;

	//instantiate 8 full adders, with wire feeding c_in value from first adder
	full_adder f0(a[0],b[0],c_in,s[0], w[0]);
	full_adder f1(a[1],b[1],w[0],s[1],w[1]);
	full_adder f2(a[2],b[2],w[1],s[2],w[2]);
	full_adder f3(a[3],b[3],w[2],s[3],w[3]);
	full_adder f4(a[4],b[4],w[3],s[4],w[4]);
	full_adder f5(a[5],b[5],w[4],s[5],w[5]);
	full_adder f6(a[6],b[6],w[5],s[6],w[6]);
	full_adder f7(a[7],b[7],w[6],s[7],c_out);
endmodule



