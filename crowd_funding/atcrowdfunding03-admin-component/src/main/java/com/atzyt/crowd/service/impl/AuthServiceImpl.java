package com.atzyt.crowd.service.impl;

import com.atzyt.crowd.entity.Auth;
import com.atzyt.crowd.entity.AuthExample;
import com.atzyt.crowd.mapper.AuthMapper;
import com.atzyt.crowd.service.api.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private AuthMapper authMapper;

	@Override
	public List<Auth> getAll() {
		return authMapper.selectByExample(new AuthExample());
	}

	@Override
	public List<Integer> getAssignedAuthIdByRoleId(Integer roleId) {
		return authMapper.selectAssignedAuthIdByRoleId(roleId);
	}

	@Override
	public void saveRoleAuthRelathinship(Map<String, List<Integer>> map) {
		
		// 1.获取roleId的值
		List<Integer> roleIdList = map.get("roleId");
		Integer roleId = roleIdList.get(0);
		
		// 2.删除旧关联关系数据
		authMapper.deleteOldRelationship(roleId);
		
		// 3.获取authIdList
		List<Integer> authIdList = map.get("authIdArray");
		
		// 4.判断authIdList是否有效
		if(authIdList != null && authIdList.size() > 0) {
			authMapper.insertNewRelationship(roleId, authIdList);
		}
	}

}
