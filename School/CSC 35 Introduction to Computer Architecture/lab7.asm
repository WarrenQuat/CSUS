
.model small
.stack 100h


.data
TestString01	db  	"***********************************",0dh,0ah,"*    PROGRAM 5 TESTING PROCEDURE*",0dh,0ah,"***********************************",0dh,0ah,'$'


TestString02	db	0dh,0ah,"WriteBin: Printing a binary number....: ",'$'

TestString03	db	0dh,0ah,"ReadChar: Type an ASCII character.....: ",'$'

TestString04	db	0dh,0ah,"Writechar: That character was.........: ",'$'

TestString05	db	0dh,0ah,"ReadString: Type an ASCII String......: " ,'$'

TestString06	db	0dh,0ah,"WriteString...........................: ",'$'

TestString07	db	0dh,0ah,"ReadDec: Type a 16bit Decimal number..: ",'$'

TestString08	db	0dh,0ah,"WriteDec: The Number was..............: ",'$'

TestString09	db	0dh,0ah,"ReadHex: Type a 16bit Hex number......: ",'$'

TestString10	db	0dh,0ah,"WriteHex:The Number was...............: ",'$'

TestString11	db	0dh,0ah,"WriteDec: Number of Characters Typed..: ",'$'

Buffer		db	10 DUP ('!')

.code
main proc
    mov ax ,@data

    mov ds,ax


    call clrscr				;Clear the Screen	

    mov dx, offset TestString01		;First Large Prompt/Header
 
    call writestring

    call crlf


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

    call crlf

    mov dx, offset TestString03		;Prompt to Test ReadChar

    call WriteString;

    call Readchar			; Reads Filtered Char into AL

    call crlf


    mov dx, offset TestString04		;Prompt for WriteChar

    call WriteString

    call Writechar			; Writes ASCII char in AL to Screen

    call crlf

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

    call crlf

    mov dx, offset TestString05		;Prompt for ReadString

    call WriteString

    mov dx, offset buffer		; Where to store the read-in-String

    mov cx, 9

    call ReadString			;Stores typed string to where DX points

    ;call crlf

    ;mov dx, offset TestString06	; Prompt for WriteDec, which will

    ;call WriteString			; print out the number of characters typed

    ;Call WriteDec

    ;Call Crlf

    ;mov dx, offset TestString06		; Print out the string that was entered

    ;call WriteString;			; uses DX register for source

    mov dx, offset buffer

    call writeString

    ;call crlf

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   ; call crlf

   ; mov dx, offset TestString07		;Prompt for ReadDec Test

   ; call WriteString

   ; call ReadDec			; puts value in AX

   ; call crlf

   ; mov dx, offset TestString08		; Prompt for WriteDec Test

   ; call WriteString;

   ; call WriteDec			; Print AX register in Decimal

   ; call crlf


   ; mov dx, offset TestString02		; Prompt for WriteBin test

   ; call WriteString;

   ; call writebin			; Prints AX register in Binary

   ; call crlf

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

   ; call crlf

   ; mov dx, offset TestString09		; Prompt for READHEX

   ; call WriteString;

   ; call ReadHex

   ; call crlf

   ; mov dx, offset TestString10		; Prompt for WRITEHEX

   ; call WriteString

   ; call WriteHex			; Prints AX register in Hexidecimal
    mov ax, 4c00h
    int 21h
main endp


crlf proc
	mov dl,10
	mov ah,06h
	int 21h
	mov dl, 13
	mov ah, 06h
	int 21h
	ret
crlf endp

clrscr proc
	mov ah, 06h
	mov al,0
	mov ch,0
	mov cl,0
	mov dh, 24
	mov dl,79
	mov bh,7
	int 10h
	ret
clrscr endp

writeString proc
	mov si, dx
	charLoop:
	  mov bl, [si]
	  mov ah, 06h
	  mov dl,bl
	  int 21h
	  mov bl,al
	  inc si
	  mov bl,[si]
	  cmp bl ,24h
	  jne charLoop
   	ret	
writeString endp

readChar proc

	top:
	mov ah, 06h
	mov dl, 0ffh
	int 21h
	jz top
	mov bh,al
	call writeChar
	ret
readChar endp

writeChar proc
	mov ah, 06h
	mov dl,bh
	int 21h
	mov bl,al
	ret
writeChar endp

readString proc
	mov si, dx
	stringLoop:
	   call readchar
	   cmp al, 0dh
	   mov [si], cl

	   je endj
	   inc si

	   loop stringLoop
	   endj:
	ret	
readString endp
	
readDec proc

readDec endp


writeDec proc

writeDec endp


readHex proc


readHex endp


writeHex proc

writeHex endp	


end main
