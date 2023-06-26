package com.dashboard.back.auth.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class testModel {
	private String title;
	private String content;
	private String writeDate;
}
