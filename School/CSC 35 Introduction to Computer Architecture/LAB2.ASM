.model small
.stack 100h
.data
X dw 40
Y dw 24
loc dw ?
sum dw ?
W dw ?

.code
    main proc
    mov  ax, @data
    mov ds, ax
    mov ax , 160
    mul Y
    mov Y, ax
    mov ax, 2
    mul X
    mov X, ax
    mov ax, Y
    add Y, ax
    mov ax, x
    mov loc, ax
    mov Y,2000
    sub Y, 1
    mov dx, 0
    mov ax, loc
    mov cx, 4
    div cx
    add ax, Y
    mov sum, ax

    mov dx, 0
    mov ax , sum
    mov cx,  7
    div cx
    mov w, dx



    mov ax,4c00h
    int 21h
    main endp
	end main
