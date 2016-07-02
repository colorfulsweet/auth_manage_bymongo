package com.system.tags;

import java.io.IOException;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.log4j.Logger;

/**
 * 根据字典与字典项编码在页面上显示字典项名称的JSP自定义标签
 * @author 结发受长生
 *
 */
public class ClauseNameTag extends TagSupport {
	private static final long serialVersionUID = -4777987538479947134L;
	private static final Logger log = Logger.getLogger(ClauseNameTag.class);
	private String dictCode;//字典编码
	private String clauseCode;//字典项编码
	private Map<String,String> dictMap;
	@Override
	public int doStartTag() throws JspException {
		dictMap = SelectClauseTag.bulidDictMap(pageContext,dictCode);
		return SKIP_BODY;
	}
	@Override
	public int doEndTag() throws JspException {
		JspWriter out = pageContext.getOut();
		try {
			out.print(dictMap.get(clauseCode)==null?"":dictMap.get(clauseCode));
			out.flush();
		} catch (IOException e) {
			log.error("字典项名称标签执行出错", e);
		}
		return EVAL_PAGE;
	}
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public String getClauseCode() {
		return clauseCode;
	}
	public void setClauseCode(String clauseCode) {
		this.clauseCode = clauseCode;
	}
	
}
