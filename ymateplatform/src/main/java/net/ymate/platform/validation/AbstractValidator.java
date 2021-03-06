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
package net.ymate.platform.validation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import net.ymate.platform.base.YMP;
import net.ymate.platform.commons.i18n.I18N;
import net.ymate.platform.commons.util.ClassUtils;
import net.ymate.platform.commons.util.ExpressionUtils;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * AbstractValidator
 * </p>
 * <p>
 * 验证器接口抽象实现类;
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
 *          <td>2013-4-14下午6:22:34</td>
 *          </tr>
 *          </table>
 */
public abstract class AbstractValidator implements IValidator {

	/**
	 * 数据验证成功标识
	 */
	protected static final String VALIDATE_SUCCESS = null;

	/**
	 * @param context 验证器上下文对象
	 * @param message 回应消息字符串
	 * @param replaceTexts 结果字符串中需要替换的数据集合
	 * @return 加载验证结果回应字符串，支持I18N，并按数组下标对结果中占位符进行替换
	 */
	protected String doMessageResult(IValidateContext context, String message, String...replaceTexts) {
		String _returnValue = context.getMessage();
		if (StringUtils.isBlank(_returnValue)) {
			_returnValue = StringUtils.defaultIfEmpty(I18N.formatMessage(YMP.__LSTRING_FILE, null, null, message), message);
		}
		if (StringUtils.isNotBlank(_returnValue) && replaceTexts != null && replaceTexts.length > 0) {
			 ExpressionUtils _exp = ExpressionUtils.bind(_returnValue);
			 for (int _idx = 0; _idx < replaceTexts.length; _idx++) {
				 _exp.set(_idx + "", replaceTexts[_idx]);
			 }
			 _returnValue = _exp.getResult();
		 } else {
			 _returnValue = I18N.formatMessage(YMP.__LSTRING_FILE, null, null, "ymp.validation.default_message");
		 }
		return _returnValue;
	}

	/**
	 * @param context 验证器上下文对象
	 * @return 参数为空时由此方法处理
	 */
	protected String onValidateNull(IValidateContext context) {
		return VALIDATE_SUCCESS;
	}

	/**
	 * @param context 验证器上下文对象
	 * @return 普通参数对象验证处理过程
	 */
	protected String onValidate(IValidateContext context) {
		return VALIDATE_SUCCESS;
	}

	/**
	 * @param context 验证器上下文对象
	 * @param arrayClassType
	 * @return 数组类型参数对象验证处理过程
	 */
	protected String onValidateArray(IValidateContext context, Class<?> arrayClassType) {
		return VALIDATE_SUCCESS;
	}

	/* (non-Javadoc)
	 * @see net.ymate.platform.validation.IValidator#validate(net.ymate.platform.validation.IValidateContext)
	 */
	public String validate(IValidateContext context) {
		if (context.getFieldValue() != null) {
			if (context.getFieldValue().getClass().isArray()) {
				Class<?> _class = ClassUtils.getArrayClassType(context.getFieldValue().getClass());
				return onValidateArray(context, _class);
			}
			return onValidate(context);
		}
		return onValidateNull(context);
	}

	/**
	 * @param context 验证器上下文对象
	 * @param minLength 参数数量最小值
	 * @return 检查参数集合中是否为空
	 */
	protected boolean doParamsLengthCheck(IValidateContext context, int minLength) {
		if (context.getParams() != null && context.getParams().length >= (minLength < 0 ? 0 : minLength)) {
			return true;
		}
		return false;
	}

	/**
	 * @param context 验证器上下文对象
	 * @return 获取单一参数值
	 */
	protected String getSingleParam(IValidateContext context) {
		return context.getParams()[0];
	}

	/**
	 * @param context 验证器上下文对象
	 * @return 解析参数映射
	 */
	protected Map<String, String> getParamMaps(IValidateContext context) {
		if (context.getParams() != null && context.getParams().length > 0) {
			Map<String, String> _returnValue = new HashMap<String, String>();
			for (String _param : context.getParams()) {
				String[] _paramArray = StringUtils.split(_param, '=');
				if (_paramArray.length == 2) {
					_returnValue.put(_paramArray[0], _paramArray[1]);
				}
			}
			return _returnValue;
		}
		return Collections.emptyMap();
	}

	/**
	 * @param type
	 * @return 判断type指定的类型是否是String类型
	 */
	protected boolean isString(Class<?> type) {
		if (type.equals(String.class)) {
			return true;
		}
		return false;
	}

}
