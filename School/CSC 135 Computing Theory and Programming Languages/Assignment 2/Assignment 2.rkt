#lang racket
#|Warren Quattrocchi
  CSC 135
  Assignment 2
|#


#|make6
  +right3, left3
|#
(define (make6 n i)
    (if (or (< (abs n) 100) (< (abs i) 100)) -2
        (+ (leftN (abs n)) (rightI(abs i)))))

(define (leftN n)
    (if(> n 999)
          (leftN (/ n 10))
            (* (floor n) 1000)))

(define (rightI i) 
    (modulo i 1000))

#|Test cases
(make6 3453 9887)
(make6 -2344 455)
|#

#|concatL
||#
 (define (concatL a b)
   (cond ((not(= (length a) (length b))) '(lists are not equal length))
          (else (appendList  a  b))))

(define (atom? x)
  (and (not (null? x))
       (not (pair? x))))

 (define (appendList a b)
    (cond((null? a)'())
         ((list?  (car a))
          (cons(string-append(convert-to-string(append (car a)  (car  b)))) (appendList(cdr a) (cdr b))))
         ((atom? (car a))
          (cons(convert-to-string(list (car a)   (car b))) (appendList(cdr a) (cdr b))))))

(define (convert-to-string List)
  (eval (cons string-append (map symbol->string List))))

#|Test cases
 (concatL '((a b) (c d) (d e) ) '((f f f) (d e s) (v v v v)))
|#

#|buildList
|#

 (define (buildList n E1 m E2)
   (append (buildN n E1) (buildM m E2)))

(define (buildN n E1)
  (cond ((= n 0) '())
        ((list? E1)
         (cons E1 (buildN (- n 1) E1)))
        (else (cons E1 (buildN(- n 1) E1)))))

(define (buildM m E2)
  (cond ((= m 0) '())
        ((list? E2)
         (cons E2 (buildM (- m 1) E2)))
        (else (cons E2 (buildM (- m 1) E2)))))

#|Test cases
(buildList 5 '() 3 'B)
(buildList 2 '(a b c ) 1 'Q)
|#

#|DFA-Acceptor
|#
 (define (DFA-Acceptor A Q0 Q2 P)
      (cond ((null?  A) 
             (cond ((equal? Q0 (car (map eval '(Q2)))) #t)
                   ((equal? Q0 Q1) #f)
                   ((equal? Q0 P ) #f)    
               (else #f)))
      (else(DFA-Acceptor (cdr A) (Q0 (car A)) Q2 P))))

 (define (Q0 A)
   (cond
        ((= A 1) Q0 )
        ((= A 0) Q1 )))

 (define (Q1 A)
   (cond
        ((= A 1) Q2 )
        ((= A 0) P)))

 (define (Q2 A)
   (cond
        ((= A 1) Q2)
        ((= A 0) Q2)))

 (define (P A)
   (cond
        ((= A 1) P)
        ((= A 0) P)))
#|Test cases
(DFA-Acceptor '(1 1 0 1 0) Q0 '(Q2) P)
(DFA-Acceptor '(1 0 0) Q0 '(Q2) P)
|#

#|selectN
|#
 (define(selectN n)
   (lambda (L)
      (cond ((= n (length L)) '())
        (else(cons(car L) ((selectN n)(cdr L)))))))

#| Test functions |#
;(define first3(selectN 3))
;(define first4(selectN 4))

