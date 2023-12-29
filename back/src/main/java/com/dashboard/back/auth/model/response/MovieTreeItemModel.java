package com.dashboard.back.auth.model.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieTreeItemModel {
    // 아이디
    private String id;

    // 이름
    private String text;

    // 태그
    private List<String> tags;

    // 노드
    private List<MovieTreeItemModel> nodes;
}
