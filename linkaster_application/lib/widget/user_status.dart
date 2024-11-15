import 'package:flutter/material.dart';

enum UserStatus {
  available,
  busy,
}

class StatusIndicator extends StatelessWidget {
  final UserStatus status;
  final double size;
  final bool withAnimation;

  const StatusIndicator({
    Key? key,
    required this.status,
    this.size = 12.0,
    this.withAnimation = true,
  }) : super(key: key);

  Color _getStatusColor(UserStatus status) {
    switch (status) {
      case UserStatus.available:
        return Colors.green;
      case UserStatus.busy:
        return Colors.red;
    }
  }

  @override
  Widget build(BuildContext context) {
    return Container(
      width: size,
      height: size,
      decoration: BoxDecoration(
        shape: BoxShape.circle,
        border: Border.all(
          color: Colors.white,
          width: size * 0.15,
        ),
      ),
      child: Center(
        child: withAnimation
            ? _AnimatedStatusDot(
                color: _getStatusColor(status),
                size: size * 0.7,
              )
            : Container(
                width: size * 0.7,
                height: size * 0.7,
                decoration: BoxDecoration(
                  color: _getStatusColor(status),
                  shape: BoxShape.circle,
                ),
              ),
      ),
    );
  }
}

class _AnimatedStatusDot extends StatefulWidget {
  final Color color;
  final double size;

  const _AnimatedStatusDot({
    Key? key,
    required this.color,
    required this.size,
  }) : super(key: key);

  @override
  _AnimatedStatusDotState createState() => _AnimatedStatusDotState();
}

class _AnimatedStatusDotState extends State<_AnimatedStatusDot>
    with SingleTickerProviderStateMixin {
  late AnimationController _controller;
  late Animation<double> _animation;

  @override
  void initState() {
    super.initState();
    _controller = AnimationController(
      duration: const Duration(seconds: 2),
      vsync: this,
    )..repeat(reverse: true);

    _animation = Tween<double>(
      begin: 0.85,
      end: 1.0,
    ).animate(CurvedAnimation(
      parent: _controller,
      curve: Curves.easeInOut,
    ));
  }

  @override
  void dispose() {
    _controller.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return AnimatedBuilder(
      animation: _animation,
      builder: (context, child) {
        return Container(
          width: widget.size,
          height: widget.size,
          decoration: BoxDecoration(
            color: widget.color,
            shape: BoxShape.circle,
            boxShadow: [
              BoxShadow(
                color: widget.color.withOpacity(0.3),
                blurRadius: 4 * _animation.value,
                spreadRadius: 2 * _animation.value,
              ),
            ],
          ),
        );
      },
    );
  }
}
