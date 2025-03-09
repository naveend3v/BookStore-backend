package com.naveend3v.bookshop.exceptions;

public class ProductNotExistException extends IllegalArgumentException{
    public ProductNotExistException(String msg){
        super(msg);
    }
}
