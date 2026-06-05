package com.example.velora

import kotlin.random.Random

data class GalleryItem(
    val id: String,
    val image: String,
    val title: String,
    val description: String,
    val category: String,
    var likes: Int,
    val creator: String,
    val uploadDate: String,
    var isLiked: Boolean = false,
    // Tambahan simulasi dinamis untuk views dan komentar
    var views: Int = Random.nextInt(likes + 100, likes * 3 + 500),
    var comments: Int = Random.nextInt(5, (likes / 10).coerceAtLeast(10))
)

object DummyDataRepository {
    fun generatePremiumGallery(): List<GalleryItem> {
        return listOf(
            GalleryItem("1", "https://images.unsplash.com/photo-1607604276583-eef5d076aa5f?q=80&w=600", "Cyberpunk Station Neon", "A deep dive into Neo-Tokyo subway lines.", "cyberpunk", 1240, "Ben Dev", "June 2026"),
            GalleryItem("2", "https://images.unsplash.com/photo-1618005182384-a83a8bd57fbe?q=80&w=600", "Neural Networks Fluid", "Generative AI visualization layers.", "AI", 892, "Alex_Framer", "May 2026"),
            GalleryItem("3", "https://images.unsplash.com/photo-1504917595217-d4dc5ebe6122?q=80&w=600", "Minimalist Workspace setup", "Dual Pro Display XDR setup on walnut desk.", "workspace", 2311, "Linear_Design", "2 hours ago"),
            GalleryItem("4", "https://images.unsplash.com/photo-1600585154340-be6161a56a0c?q=80&w=600", "Futuristic Concrete Villa", "Parametric architecture integrated into nature.", "architecture", 456, "Zaha_Core", "Yesterday"),
            GalleryItem("5", "https://images.unsplash.com/photo-1542751371-adc38448a05e?q=80&w=600", "Immersive Gaming Rig", "OLED curved battlestation with cold purple ambient.", "gaming", 1983, "Asus_Nexus", "3 days ago"),
            GalleryItem("6", "https://images.unsplash.com/photo-1451187580459-43490279c0fa?q=80&w=600", "Deep Space Quantum Core", "Telemetry stream data analysis visualization.", "technology", 3410, "NASA_Ecosystem", "Just Now"),
            GalleryItem("7", "https://images.unsplash.com/photo-1579546929518-9e396f3cc809?q=80&w=600", "Holographic UI Elements", "Glassmorphic overlay for web application dashboard.", "futuristic", 760, "Framer_Wizard", "June 2026"),
            GalleryItem("8", "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?q=80&w=600", "Cyber-coast Horizon", "Synthesizer sunset over simulated ocean node.", "landscape", 540, "Neon_Driver", "May 2026"),
            GalleryItem("9", "https://images.unsplash.com/photo-1614741118887-7a4ee193a5fa?q=80&w=600", "Monolith Datacenter", "Rows of high computing units serving neural layers.", "technology", 1650, "Ben Dev", "1 week ago"),
            GalleryItem("10", "https://images.unsplash.com/photo-1511512578047-dfb367046420?q=80&w=600", "Cyberpunk Alleyway 2077", "Rain reflection on commercial cyber corridors.", "cyberpunk", 4200, "V_Mercenary", "June 2026"),
            GalleryItem("11", "https://images.unsplash.com/photo-1634017839464-5c339ebe3cb4?q=80&w=600", "Glass Abstract Prism", "Light refraction simulated through custom shaders.", "futuristic", 890, "Layers_App", "2 weeks ago"),
            GalleryItem("12", "https://images.unsplash.com/photo-1536440136628-849c177e76a1?q=80&w=600", "AI Android Construction", "Robotic limb assembly blueprints.", "AI", 1120, "Cyberdyne_Sys", "June 2026"),
            GalleryItem("13", "https://images.unsplash.com/photo-1555066931-4365d14bab8c?q=80&w=600", "Clean IDE Terminal Layout", "Code optimization routine compiling.", "workspace", 3100, "Ben Dev", "3 days ago"),
            GalleryItem("14", "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?q=80&w=600", "Neo-Brutalist HQ", "Raw concrete geometric shape lines.", "architecture", 670, "Pinterest_Architect", "May 2026"),
            GalleryItem("15", "https://images.unsplash.com/photo-1538481199705-c710c4e965fc?q=80&w=600", "Tokyo Arcade Chamber", "Retro neon mechanical vibe.", "gaming", 1540, "Akira_99", "June 2026"),
            GalleryItem("16", "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?q=80&w=600", "SaaS Infrastructure Command", "Global load balancer dashboard view.", "technology", 990, "Vercel_Fan", "Today"),
            GalleryItem("17", "https://images.unsplash.com/photo-1618172193763-c511deb635ca?q=80&w=600", "Floating Abstract Orb", "3D Blender sculpture.", "futuristic", 1320, "Dribbble_Star", "June 2026"),
            GalleryItem("18", "https://images.unsplash.com/photo-1506744038136-46273834b3fb?q=80&w=600", "Yosemite Vector Valley", "Crisp morning mist inside high valley.", "landscape", 2450, "Nature_Grid", "April 2026"),
            GalleryItem("19", "https://images.unsplash.com/photo-1563089145-599997674d42?q=80&w=600", "Neon Laser Grid Array", "Light projection setup.", "cyberpunk", 1880, "Grid_Runner", "June 2026"),
            GalleryItem("20", "https://images.unsplash.com/photo-1620641788421-7a1c342ea42e?q=80&w=600", "Biometric Mesh Grid", "Face tracking vector interface.", "AI", 1430, "Miri_AI", "June 2026")
        )
    }
}