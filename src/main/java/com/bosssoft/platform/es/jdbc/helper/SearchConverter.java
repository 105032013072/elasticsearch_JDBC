/*******************************************************************************
 * $Header$
 * $Revision$
 * $Date$
 *
 *==============================================================================
 *
 * Copyright (c) 2001-2016 Bosssoft Co, Ltd.
 * All rights reserved.
 * 
 * Created on 2016年12月22日
 *******************************************************************************/


package com.bosssoft.platform.es.jdbc.helper;

import java.util.ArrayList;
import java.util.List;

import com.bosssoft.platform.es.jdbc.enumeration.AggType;
import com.bosssoft.platform.es.jdbc.mate.ColumnMate;
import com.bosssoft.platform.es.jdbc.mate.OrderbyMate;
import com.bosssoft.platform.es.jdbc.mate.PageMate;
import com.bosssoft.platform.es.jdbc.model.ConditionExp;
import com.bosssoft.platform.es.jdbc.model.SqlObj;
import com.facebook.presto.sql.tree.Expression;
import com.facebook.presto.sql.tree.FunctionCall;
import com.facebook.presto.sql.tree.QualifiedNameReference;
import com.facebook.presto.sql.tree.Query;
import com.facebook.presto.sql.tree.QueryBody;
import com.facebook.presto.sql.tree.QuerySpecification;
import com.facebook.presto.sql.tree.Select;
import com.facebook.presto.sql.tree.SelectItem;
import com.facebook.presto.sql.tree.SingleColumn;
import com.facebook.presto.sql.tree.Statement;

/**
 * TODO 此处填写 class 信息
 *
 * @author huangxuewen (mailto:huangxuewen@bosssoft.com.cn)
 */

public class SearchConverter {
	
	public SqlObj convent(Statement statement,String sql){
		SqlObj obj=new SqlObj();
		QueryBody qb=((Query)statement).getQueryBody();
		obj.setDistinct(conventDistinct(qb));
		obj.setFrom(conventFrom(qb));
		obj.setGroupby(conventGroupby(qb));
		obj.setHaving(conventwhere(qb));
		obj.setLimit(conventLimit(sql));
		obj.setOrderby(conventOrderBy(qb));
		obj.setSelectItems(conventSlect(qb));
		obj.setWhere(conventwhere(qb));
		 /* System.out.println(((QuerySpecification)qb).getSelect().toString());
		 System.out.println(((QuerySpecification)qb).getWhere().toString());*/
	     
		 Expression node=((QuerySpecification)qb).getHaving().get();
		
		
		
		
		
	    return obj;
	}



	/**
	 * @param qb
	 * @return
	 */
	private List<ColumnMate> conventSlect(QueryBody qb) {
		Select select=((QuerySpecification)qb).getSelect();
		List<SelectItem> list=select.getSelectItems();
		List<ColumnMate> result=new ArrayList<>();
		for (SelectItem selectItem : list) {
			ColumnMate columnMate=new ColumnMate();
            SingleColumn sc=(SingleColumn) selectItem;
            
            //设置别名
            if(sc.getAlias().isPresent())
                 columnMate.setAlias(sc.getAlias().get().toString());
            
            
            //根据是否是聚合函数来设置字段名和聚合函数类型
            Expression exp =sc.getExpression();
            if(exp instanceof FunctionCall){
            	FunctionCall fc=(FunctionCall) exp;
                columnMate.setAggType(AggType.valueOf(fc.getName().toString().toUpperCase()));
                columnMate.setName(fc.getArguments().get(0).toString());;
            }else {
            	QualifiedNameReference qnf=(QualifiedNameReference) exp;
            	columnMate.setName(qnf.getName().toString());
            	
            }
            
            result.add(columnMate);
            
		}
		
		return result;
	}



	/**
	 * @param qb
	 * @return
	 */
	private List<OrderbyMate> conventOrderBy(QueryBody qb) {
		// TODO Auto-generated method stub
		return null;
	}



	/**
	 * @param sql
	 * @return
	 */
	private PageMate conventLimit(String sql) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @param qb
	 * @return
	 */
	private List<ConditionExp> conventwhere(QueryBody qb) {
		
		return null;
	}

	/**
	 * @param qb
	 * @return
	 */
	private List<ColumnMate> conventGroupby(QueryBody qb) {
		
		return null;
	}

	/**
	 * @param qb
	 * @return
	 */
	private String conventFrom(QueryBody qb) {
		
		return ((QuerySpecification)qb).getFrom().get().toString();
	}

	/**
	 * 解析获取Distinct
	 * @param qb
	 * @return
	 */
	private Boolean conventDistinct(QueryBody qb) {
		Select select=((QuerySpecification)qb).getSelect();
		return select.isDistinct();
	}

}

/*
 * 修改历史
 * $Log$ 
 */