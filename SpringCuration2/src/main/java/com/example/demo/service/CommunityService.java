package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.model.Community;
import com.example.demo.model.CommunityTags;
import com.example.demo.repository.CommunityRepository;
import com.example.demo.repository.CommunityTagsRepository;

@Service
public class CommunityService {

	@Autowired
	private CommunityRepository repository;

	@Autowired CommunityTagsRepository tagRepository;


    @Transactional
    public void registerCommunity(String name,Long adminId, String content, String keyword) {
        Community community = new Community(name, adminId, content);
    		String[] tagArray = keyword.split("[,、/／　\t\\s]+");
    		Set<CommunityTags> tagSet = new HashSet<CommunityTags>();
    		for(String tag: tagArray) {
    			CommunityTags cTag = new CommunityTags(tag);
    			tagSet.add(cTag);
    			community.addTags(cTag);
    		}
    		community.setTags(tagSet);
        repository.save(community);
    }

    @Transactional
    public void editCommunity(Long communityId, String content, String keyword) {
    		Community community = repository.findById(communityId);
    		String[] tagArray = keyword.split("[,、/／　\t\\s]+");
    		Set<CommunityTags> tagSet = new HashSet<CommunityTags>();
    		for(String tag: tagArray) {
    			CommunityTags cTag = new CommunityTags(tag);
    			tagSet.add(cTag);
    			community.addTags(cTag);
    		}
    		community.setTags(tagSet);
        repository.save(community);
    }

    @Transactional
    public void deleteCommunity(Long communityId) {
    		repository.deleteById(communityId);
    }

	@Transactional
    public List<Community> searchCommunity(String keywords){
		String[] keywordList = keywords.split("[,、／/\\s]+");
    		List<Community> communityList = new ArrayList<Community>();
    		for(String keyword : keywordList) {
    			List<Community> com = repository.findByKeyword(keyword);
    			for(int i=0; i<com.size();i++) {
        			communityList.add(com.get(i));
    			}
    		}
    		return communityList;
    }

	@Transactional
	public List<Community> findAdminCommunity(Long adminId){

		List<Community> communityList = new ArrayList<Community>();
		if(!repository.existsByAdminId(adminId)) {
			return communityList;
		}else {
			communityList = repository.findByAdminId(adminId);
			return communityList;
		}
	}


}
