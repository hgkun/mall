/**
 * $Id: Gender.java Nov 18, 2014 10:54:31 AM hdp
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.shopping.base.enums;

/**
 * 性别

 * @version V1.0
 * @since V1.0
 */
public enum Gender {
	MAN("男"),
	WOMAN("女"),
	UNKNOWN("未知");
	
	private Gender(String value) {
		this.value = value;
	}
	
	private String value;
	
	public String getValue() {
		return value;
	}
}