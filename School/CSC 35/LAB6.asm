;Warren Quattrocchi
;CSC 35
;Spring 2018
;Instructor: Dr Ghansah
;Monday	March 16, 2018, 3:00 pm
;Lab section #7
;Program #6
;
;This programs performs arithmetic on user inputted values of up to 4 digits
;

.model small
.stack 100h
.data
X dw ?		;declare and initialize X
Y dw ?		;decalre and initialize Y
char db ?	;delcare a byte to hold each number
loc dw ?	;declare unitialized variable loc
sum dw ?	;uninitialized variable sum
W dw ?		;unitialized variable W
inputX	db 0ah,0dh, "Input X =", '$'  ;x input prompt
calcW	db 0ah,0dh, "Calculating W...", '$' ; calculating w
inputY	db 0ah,0dh, "Input Y =", '$'  ;y input prompt
outputW db 0ah,0dh, "Output W =", '$' ;output prompt
prompt	db 0ah,0dh, "Press any key to continue (q to exit)", '$'


.code
 main proc

top:
    mov dx, 0
    mov cx, 0
    mov  ax, @data
    mov ds,ax
    mov bx,x
    mov dx, OFFSET inputX
    CALL INPUTVAL
    mov x,bx

    mov bx,y
    mov dx, OFFSET inputY
    CALL INPUTVAL
    mov y,bx

    mov dx,OFFSET calcW
    mov ah,9
    int 21h	
 
    mov si,y
    mov di, x
			    
    CALL CALCULATELOC
    mov loc, ax       ;move result into loc

    mov bx, y
    mov si, loc
    CALL CALCULATESUM
    mov sum,di
    
    mov ax, sum
    CALL CALCULATEW  
    mov w, dx
    
    mov si, w      
    CALL OUTPUTWVAL

    mov dx, OFFSET prompt
    mov ah,9
    int 21h
    mov ah,1
    int 21h
    cmp al, 71h
    jne top
    mov ax,4c00h
    int 21h
MAIN ENDP

INPUTVAL PROC
    mov ah,9			      ;display inputx
    int 21h
    mov cx, 4                         ;set loop counter to 4
    l1:				      ;loop 1 for x input
     mov ah,1
     int 21h
     mov char, al                     ;prompt user for value
     sub char, 30h		      ;convert ASCII to decimal
     mov ax, 10			      ;multiply value by 10 and add input to it
     mul bx
     mov bx, ax
     add bx, WORD ptr [char]          ;add character into bx
     loop l1			      ;end of loop
     RET
INPUTVAL endp

CALCULATELOC PROC
    mov ax , 160   ;move 160 into ax reg
    mul si	   ;mul y by ax
    mov dx, ax
    mov ax, 2	   ;move 2 into ax
    mul dl	   ;mul x by ax
    add ax, dx	   ;add x and ax
    ret
CALCULATELOC ENDP
     
CALCULATESUM PROC
    mov bx,1000	   ;move 1000 into y
    sub bx, 1	   ;subtract 1 from y
    mov dx, 0	   ;clear dx
    mov ax, si     ;mov loc into ax
    mov cx, 16	   ;mov 16 into cx for division
    div cx	   ;divide ax b cx
    add ax, bx	   ;add y to result
    mov di, ax     ;mov ax into sum
    mov dx,0	   ;clear dx
    mov ax,bx      ;mov y into ax
    mov cx,4	   ;mov 4 into cx
    div cx         ;divide ax by cx
    add di ,ax     ;mov result into sum
    mov dx,0	   ;clear dx
    mov ax,bx      ;mov y into ax
    mov cx,200     ;mov 200 in cx
    div cx	   ;divide ax by cx
    add di,ax	   ;ad result into sum
    mov dx, 0	   ;clear dx
    ret
CALCULATESUM ENDP
                              
CALCULATEW PROC
    mov cx,  7	   ;mov 7 into cx
    div cx	   ;divide ax by cx
    add dx, 5	   ;add 5 to the resulting remainder
    RET
CALCULATEW ENDP

OUTPUTWVAL PROC 
    mov bx, 0
    mov ax,si

    l2:
     mov dx, 0
     mov cx, 10
    
     div cx
     push dx
     add bx, 1
     mov dx, 0
     cmp bx, 4
     JNE l2  
  
    mov ah, 9
    mov dx, offset outputW   ;display output message
    int 21h

    l3:   
     pop ax  
     add ax,30h
     mov ah,06h	   ;display single ASCII char on screen
     mov dl,al
     int 21h
     sub bx, 1
     cmp bx, 0
     JNE l3

    RET
OUTPUTWVAL ENDP
    end main
