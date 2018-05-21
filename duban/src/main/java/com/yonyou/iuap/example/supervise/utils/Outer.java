package com.yonyou.iuap.example.supervise.utils;

public class Outer<E>
{
	private E value;
	
	public Outer()
	{
	}
	
	public Outer(E value)
	{
		this.value = value;
	}
	
	public E value()
	{
		return value;
	}

	public void setValue(E value)
	{
		this.value = value;
	}
}
