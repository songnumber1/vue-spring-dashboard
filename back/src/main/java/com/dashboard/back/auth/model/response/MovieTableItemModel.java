package com.dashboard.back.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovieTableItemModel {
    private String id;

    private String absolutePath;

    private boolean isDir;

    private String name;

    private String writeDate;
}
