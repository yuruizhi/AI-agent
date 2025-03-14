package com.chy.agents.image.service;

import com.chy.agents.image.model.Media;
import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Base64;

/**
 * 图像分析服务
 *
 * @author YuRuizhi
 * @date 2025/3/12
 */
@Service
public class ImageAnalysisService {

    // 支持视觉能力的ChatClient
    private final ChatClient visionChatClient;
    
    @Autowired
    public ImageAnalysisService(ChatClient visionChatClient) {
        this.visionChatClient = visionChatClient;
    }
    
    /**
     * 分析图像
     *
     * @param imageData 图像数据
     * @param prompt 分析提示
     * @return 分析结果
     */
    public String analyzeImage(byte[] imageData, String prompt) {
        String base64Image = Base64.getEncoder().encodeToString(imageData);
        Media imageMedia = new Media("image/jpeg", base64Image);
        UserMessage userMessage = new UserMessage(prompt, List.of(imageMedia));
        
        Prompt imagePrompt = new Prompt(List.of(userMessage));
        return visionChatClient.call(imagePrompt).getResult().getOutput().getContent();
    }
} 