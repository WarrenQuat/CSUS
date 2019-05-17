`timescale 1ns / 1ps
module carry_tb();
	reg [7:0]a;
	reg [7:0]b;
	reg c_in;
	wire [7:0]s;
	wire c_out;
	integer i;

	carry_adder dut(.a(a),.b(b),.c_in(c_in),.s(s),.c_out(c_out));
	
	initial begin

		 $display("\nWarren Quattrocchi");
		//waits 5ns between each test
			#5 a = 3;b=2;c_in = 0;
			#5 a = 1;b=4;c_in = 0;
			#5 a = 0;b=7;c_in = 1;	
			#5 a = 3;b=1;c_in = 0;	
			#5 a = 3;b=9;c_in = 1;	
			#5 a = 3;b=2;c_in = 1;	
			#5 a = 12;b=3;c_in = 0;	
			#5 a = 14;b=1;c_in = 1;	
			#5 a = 0;b=13;c_in = 0;
			#5 a = 1;b=9;c_in = 1;		
			
		
		#5 $stop;
		end
			initial begin

			//monitors time and values in decimal form
			$monitor ("time = %3d,a = %d, b =%d, c_in= %d, c_out = %d, s = %d \n",
				  $time, a, b, c_in, c_out, s, );
			end 
endmodule