# Ani

[dmhy]: http://www.dmhy.org/

[Bangumi]: http://bangumi.tv

[Compose Multiplatform]: https://www.jetbrains.com/lp/compose-mpp/

集找番、追番、看番的一站式追番平台。

使用 Bangumi 的番剧索引以及观看记录功能，支持 [动漫花园][dmhy] 等下载源，未来接入弹弹play 等平台实现在线播放。

开发重点在于追番和找番体验，以及多平台。Bangumi 的功能可能不会实现完善。

## 3.0 开发进程

Ani 3.0 完全重写, 继续使用 [Compose Multiplatform] 实现多平台, 将私有追番进度服务器改为使用 Bangumi,
并从 Bangumi 获取番剧信息以及相关评论等.

## 下载

Ani 支持 macOS、Linux、Windows 与 Android。
可在 [releases](https://github.com/Him188/ani/releases/latest) 中的 "Assets" 下载最新正式版本。

测试版本可以在 [releases](https://github.com/Him188/ani/releases/) 找到。使用测试版本可以体验最新特性，但可能不稳定。

## 参与开发

欢迎你提交 [PR](https://github.com/Him188/ani/pulls) [参与开发](CONTRIBUTING.md)。

## 提示

#### 访问动漫花园

动漫花园在中国大陆无法通过 IPv4 访问。你可能需要一些技术手段，或者在一个有 IPv6 的环境 (例如数据网络)
，才能正常使用。

在桌面端，可以在设置（Windows 在标题栏，macOS 在屏幕左上角点击"动漫花园"）中设置使用代理。代理是默认禁用的。初始的
HTTP
代理设置为连接本地 Clash 并使用 Clash 的默认端口。

#### 额外设置

部分桌面端会支持额外设置，这些设置都可以由上述方法看到，如果看不到就是没有。例如 macOS
端支持窗口沉浸（将背景颜色绘制到标题栏内，默认启用）。

## 2.0 版本历史功能

### 查看最新话题

打开应用即可看到最新话题列表。话题列表是一个无限流，当滑动到最低端时，它将加载更多内容。

![](.README_images/main-appearance.png)

### 以作品为中心搜索

可根据关键词搜索话题。应用将自动识别该关键词对应的作品信息，并统合搜索结果，以作品为中心查看结果。

![](.README_images/searching-for-topics-by-keywords.png)

### 过滤标签

在统合搜索结果中点击标签可以执行过滤，快速查找想要的内容。

![](.README_images/filtering-topics.png)

### 收藏作品并记忆搜索偏好和观看记录

点击卡片右上角的五角星即可添加或取消收藏。收藏的作品会记忆搜索偏好以及剧集观看记录。在查看收藏列表时会自动更新剧集列表，点击剧集可跳转搜索。

![](.README_images/star-and-remembering.gif)

### 多端即时同步

使用同一个同步 token 时，在一个客户端进行修改可以即时同步到其他在线客户端，确保流畅体验。

要启用同步，请在设置中勾选 "与服务器同步"。所有客户端都支持多端即时同步。

### 界面简洁流畅

[MD3]: https://m3.material.io/

应用采用 [质感设计 3][MD3]，界面简洁而功能齐全。所有动作都配有流畅的动画。针对多平台设计，应用支持无极可调窗口大小。

![](.README_images/resizable-window.gif)
