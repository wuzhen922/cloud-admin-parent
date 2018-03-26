package com.eyelake.cloud.admin.assist.dto.admin;

import org.omg.CORBA.*;
import org.omg.CORBA.Object;

/**
 * admin会员登录请求实体
 * Created by wunder on 2016/10/13 23:27.
 */
public class AdminLoginDto extends Request {

    private String userName;

    private String password;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	@Override
	public Object target() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String operation() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NVList arguments() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public NamedValue result() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Environment env() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ExceptionList exceptions() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ContextList contexts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Context ctx() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void ctx(Context c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Any add_in_arg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Any add_named_in_arg(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Any add_inout_arg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Any add_named_inout_arg(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Any add_out_arg() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Any add_named_out_arg(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void set_return_type(TypeCode tc) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Any return_value() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void invoke() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send_oneway() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void send_deferred() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean poll_response() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void get_response() throws WrongTransaction {
		// TODO Auto-generated method stub
		
	}

	
}
