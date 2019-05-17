`timescale 1ns/ 1ps

//test bench for multiplexer
module mux_tb();
	reg [3:0] a;
	reg [1:0] s;
	wire z;
	//int i for loop
	integer i;

	multiplexer dut(.a(a),.s(s),.z(z));
	
	initial begin

	    $display("\nWarren Quattrocchi");
	    //for a 4 bit input, there are 15 different combination
	    for ( i = 0; i < 16; i = i + 1 )
		begin
		    //test all select cases with each combination
		    //wait 5 ns between tests
		    #5 a = i; s = 0;
		    #5 a = i; s = 1;
		    #5 a = i; s = 2;
		    #5 a = i; s = 3;
		end
	    #5 $stop;
	   
	    end
			initial begin
			//monitor time, inputs, and output in binary
			$monitor ("time = %3d,a = %b, s = %b z = %b \n",
				  $time, a, s,z, );
			end 

endmodule