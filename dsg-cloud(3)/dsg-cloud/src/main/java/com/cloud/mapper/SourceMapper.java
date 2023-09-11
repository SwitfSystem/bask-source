package com.cloud.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.cloud.bean.Source;

public interface SourceMapper {

	Integer selectSourceCountDoc();

	ArrayList<Source> selectSourceDoc(@Param("start")Integer start, @Param("end")Integer end);

	void insertSource(Source source);

}
