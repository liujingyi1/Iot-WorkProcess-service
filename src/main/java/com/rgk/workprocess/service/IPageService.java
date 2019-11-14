package com.rgk.workprocess.service;

import com.rgk.workprocess.domain.ProcessDetailView;
import org.springframework.ui.Model;

public interface IPageService {
    public ProcessDetailView getProcessDetail(String id, Model model);
}
