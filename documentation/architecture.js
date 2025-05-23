export function drawArchitecture(ctx) {
  // 设置画布尺寸
  const width = 800;
  const height = 600;
  
  // 设置背景色
  ctx.fillStyle = '#f5f5f5';
  ctx.fillRect(0, 0, width, height);
  
  // 绘制标题
  ctx.font = 'bold 24px Arial';
  ctx.fillStyle = '#333';
  ctx.textAlign = 'center';
  ctx.fillText('极其复杂的 Hello World 系统架构', width / 2, 40);
  
  // 绘制客户端
  drawComponent(ctx, width / 2, 100, 200, 60, '#e1f5fe', '命令行客户端', 'hello-world-client');
  
  // 绘制聚合器服务
  drawComponent(ctx, width / 2, 220, 280, 60, '#e8f5e9', '聚合器服务', 'hello-world-aggregator');
  
  // 绘制 Hello 服务和 World 服务
  drawComponent(ctx, width / 3 - 50, 340, 200, 60, '#fff9c4', 'Hello 服务', 'hello-service');
  drawComponent(ctx, width * 2 / 3 + 50, 340, 200, 60, '#ffccbc', 'World 服务', 'world-service');
  
  // 绘制数据库和缓存
  drawComponent(ctx, width / 3 - 50, 460, 150, 60, '#d1c4e9', 'PostgreSQL', 'Hello 数据');
  drawComponent(ctx, width * 2 / 3 + 50, 460, 150, 60, '#bbdefb', 'MongoDB', 'World 数据');
  drawComponent(ctx, width / 2, 460, 150, 60, '#ffecb3', 'Redis 缓存', '');
  
  // 绘制支持服务
  drawComponent(ctx, 120, 530, 100, 40, '#dcedc8', 'Eureka', '服务发现');
  drawComponent(ctx, 250, 530, 100, 40, '#f8bbd0', 'Zipkin', '分布式追踪');
  drawComponent(ctx, 380, 530, 100, 40, '#b2dfdb', 'Kafka', '消息队列');
  drawComponent(ctx, 510, 530, 100, 40, '#d7ccc8', 'Prometheus', '监控');
  drawComponent(ctx, 640, 530, 100, 40, '#c5cae9', 'Grafana', '可视化');
  
  // 绘制连接线
  // 客户端到聚合器
  drawArrow(ctx, width / 2, 130, width / 2, 190);
  
  // 聚合器到服务
  drawArrow(ctx, width / 2 - 40, 250, width / 3, 320);
  drawArrow(ctx, width / 2 + 40, 250, width * 2 / 3, 320);
  
  // 服务到数据库
  drawArrow(ctx, width / 3 - 50, 370, width / 3 - 50, 430);
  drawArrow(ctx, width * 2 / 3 + 50, 370, width * 2 / 3 + 50, 430);
  
  // 服务到缓存
  drawArrow(ctx, width / 3, 370, width / 2 - 40, 440);
  drawArrow(ctx, width * 2 / 3, 370, width / 2 + 40, 440);
  
  // 图例
  ctx.font = '12px Arial';
  ctx.fillStyle = '#666';
  ctx.textAlign = 'left';
  ctx.fillText('* 系统同时使用了断路器、重试、限流和舱壁模式进行容错处理', 50, 590);
}

function drawComponent(ctx, x, y, width, height, color, title, subtitle) {
  // 绘制阴影
  ctx.shadowColor = 'rgba(0, 0, 0, 0.2)';
  ctx.shadowBlur = 10;
  ctx.shadowOffsetX = 3;
  ctx.shadowOffsetY = 3;
  
  // 绘制圆角矩形
  ctx.beginPath();
  ctx.moveTo(x - width / 2 + 10, y - height / 2);
  ctx.lineTo(x + width / 2 - 10, y - height / 2);
  ctx.quadraticCurveTo(x + width / 2, y - height / 2, x + width / 2, y - height / 2 + 10);
  ctx.lineTo(x + width / 2, y + height / 2 - 10);
  ctx.quadraticCurveTo(x + width / 2, y + height / 2, x + width / 2 - 10, y + height / 2);
  ctx.lineTo(x - width / 2 + 10, y + height / 2);
  ctx.quadraticCurveTo(x - width / 2, y + height / 2, x - width / 2, y + height / 2 - 10);
  ctx.lineTo(x - width / 2, y - height / 2 + 10);
  ctx.quadraticCurveTo(x - width / 2, y - height / 2, x - width / 2 + 10, y - height / 2);
  ctx.closePath();
  
  // 填充背景色
  ctx.fillStyle = color;
  ctx.fill();
  
  // 取消阴影
  ctx.shadowColor = 'transparent';
  ctx.shadowBlur = 0;
  ctx.shadowOffsetX = 0;
  ctx.shadowOffsetY = 0;
  
  // 绘制边框
  ctx.strokeStyle = '#999';
  ctx.lineWidth = 1;
  ctx.stroke();
  
  // 绘制标题
  ctx.font = 'bold 14px Arial';
  ctx.fillStyle = '#333';
  ctx.textAlign = 'center';
  ctx.fillText(title, x, y + 5);
  
  // 绘制副标题
  if (subtitle) {
    ctx.font = '12px Arial';
    ctx.fillStyle = '#666';
    ctx.fillText(subtitle, x, y + 25);
  }
}

function drawArrow(ctx, x1, y1, x2, y2) {
  const headLength = 10;
  const angle = Math.atan2(y2 - y1, x2 - x1);
  
  // 绘制线
  ctx.beginPath();
  ctx.moveTo(x1, y1);
  ctx.lineTo(x2, y2);
  ctx.strokeStyle = '#666';
  ctx.lineWidth = 1.5;
  ctx.stroke();
  
  // 绘制箭头
  ctx.beginPath();
  ctx.moveTo(x2, y2);
  ctx.lineTo(x2 - headLength * Math.cos(angle - Math.PI / 6), y2 - headLength * Math.sin(angle - Math.PI / 6));
  ctx.lineTo(x2 - headLength * Math.cos(angle + Math.PI / 6), y2 - headLength * Math.sin(angle + Math.PI / 6));
  ctx.closePath();
  ctx.fillStyle = '#666';
  ctx.fill();
}
