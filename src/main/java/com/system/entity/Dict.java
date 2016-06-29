package com.system.entity;

import java.io.Serializable;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.alibaba.fastjson.annotation.JSONType;

/**
 * 字典实体类
 * @author 结发受长生
 *
 */
@Entity("s_dict")
public class Dict implements Serializable {
	private static final long serialVersionUID = -6788795205288727827L;
	@Id
	private ObjectId id;
	private String dictCode;//字典编码
	private String dictName;//字典名称
	private String remark;//备注
	
	@Reference(ignoreMissing=true,lazy=true)
	private List<DictClause> clauses;//字典项列表
	/**
	 * 字典条目
	 * @author 结发受长生
	 *
	 */
	@JSONType(ignores={"dict"})
	@Entity("s_dict_clause")
	public static class DictClause implements Serializable{
		private static final long serialVersionUID = 292020742117220942L;
		@Id
		private ObjectId id;
		private String clauseCode;//字典条目编码
		private String clauseName;//字典条目名称
		public ObjectId getId() {
			return id;
		}
		public void setId(ObjectId id) {
			this.id = id;
		}
		public String getClauseCode() {
			return clauseCode;
		}
		public void setClauseCode(String clauseCode) {
			this.clauseCode = clauseCode;
		}
		public String getClauseName() {
			return clauseName;
		}
		public void setClauseName(String clauseName) {
			this.clauseName = clauseName;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DictClause other = (DictClause) obj;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getDictCode() {
		return dictCode;
	}
	public void setDictCode(String dictCode) {
		this.dictCode = dictCode;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<DictClause> getClauses() {
		return clauses;
	}
	public void setClauses(List<DictClause> clauses) {
		this.clauses = clauses;
	}
}
