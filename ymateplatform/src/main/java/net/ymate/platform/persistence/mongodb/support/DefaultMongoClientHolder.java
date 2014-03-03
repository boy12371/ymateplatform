/*
 * Copyright 2007-2107 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.ymate.platform.persistence.mongodb.support;

import net.ymate.platform.persistence.mongodb.IMongoClientHolder;

import com.mongodb.DB;
import com.mongodb.WriteConcern;

/**
 * <p>
 * DefaultMongoClientHolder
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author 刘镇(suninformation@163.com)
 * @version 0.0.0
 *          <table style="border:1px solid gray;">
 *          <tr>
 *          <th width="100px">版本号</th><th width="100px">动作</th><th
 *          width="100px">修改人</th><th width="100px">修改时间</th>
 *          </tr>
 *          <!-- 以 Table 方式书写修改历史 -->
 *          <tr>
 *          <td>0.0.0</td>
 *          <td>创建类</td>
 *          <td>刘镇</td>
 *          <td>2014年2月6日下午3:09:06</td>
 *          </tr>
 *          </table>
 */
public class DefaultMongoClientHolder implements IMongoClientHolder {

	protected String dataSourceName;
	protected DB db;

	protected WriteConcern concern;

	public DefaultMongoClientHolder(String dataSourceName, DB db) {
		this.dataSourceName = dataSourceName;
		this.db = db;
		this.concern = db.getWriteConcern();
	}

	/* (non-Javadoc)
	 * @see net.ymate.platform.persistence.mongodb.IMongoClientHolder#getDataSourceName()
	 */
	public String getDataSourceName() {
		return dataSourceName;
	}

	/* (non-Javadoc)
	 * @see net.ymate.platform.persistence.mongodb.IMongoClientHolder#getDB()
	 */
	public DB getDB() {
		return db;
	}

	/* (non-Javadoc)
	 * @see net.ymate.platform.persistence.mongodb.IMongoClientHolder#getWriteConcern()
	 */
	public WriteConcern getWriteConcern() {
		return this.concern;
	}

	/* (non-Javadoc)
	 * @see net.ymate.platform.persistence.mongodb.IMongoClientHolder#setWriteConcern(com.mongodb.WriteConcern)
	 */
	public void setWriteConcern(WriteConcern concern) {
		this.concern = concern;
	}

	/* (non-Javadoc)
	 * @see net.ymate.platform.persistence.mongodb.IMongoClientHolder#requestStart()
	 */
	public void requestStart() {
		this.db.requestStart();
	}

	/* (non-Javadoc)
	 * @see net.ymate.platform.persistence.mongodb.IMongoClientHolder#requestEnsureConnection()
	 */
	public void requestEnsureConnection() {
		this.db.requestEnsureConnection();
	}

	/* (non-Javadoc)
	 * @see net.ymate.platform.persistence.mongodb.IMongoClientHolder#requestDone()
	 */
	public void requestDone() {
		this.db.requestDone();
	}

	/* (non-Javadoc)
	 * @see net.ymate.platform.persistence.mongodb.IMongoClientHolder#release()
	 */
	public void release() {
		db = null;
		concern = null;
		dataSourceName = null;
	}

}
